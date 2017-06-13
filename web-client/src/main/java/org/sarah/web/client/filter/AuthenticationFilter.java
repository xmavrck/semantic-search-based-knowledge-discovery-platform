package org.sarah.web.client.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sarah.web.client.constants.Constants;
import org.sarah.web.client.entities.AccessTokens;
import org.sarah.web.client.entities.User;
import org.sarah.web.client.oauthclient1.constants.OAuthConstants;
import org.sarah.web.client.oauthclient1.types.AccessToken;
import org.sarah.web.client.oauthclient1.types.RequestToken;
import org.sarah.web.client.services.AccessTokenService;
import org.sarah.web.client.services.UserService;
import org.sarah.web.client.util.AuthorizationHeaderParser;
import org.sarah.web.client.util.ConfigUtils;
import org.sarah.web.client.util.ResponseConverterUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The class AuthenticationFilter.
 * 
 * @author chandan
 */
@WebFilter(urlPatterns = { "/", "/login", "/login/*", "/dashboard", "/api/*", "/dashboard/*" })
public class AuthenticationFilter implements Filter, Constants, Constants.Authentication, Constants.App,
		Constants.OAuthProvider, OAuthConstants.Url_OAuth2 {

	/**
	 * oAuthHttp1
	 */
	org.sarah.web.client.oauthclient1.http.OAuthHttp oAuthHttp1 = null;
	/**
	 * responseConverterUtil
	 */
	ResponseConverterUtil responseConverterUtil;
	/**
	 * authorizationHeaderParser
	 */
	AuthorizationHeaderParser authorizationHeaderParser = new AuthorizationHeaderParser();
	/**
	 * configUtils
	 */
	ConfigUtils configUtils;
	/**
	 * userService
	 */
	UserService userService;
	/**
	 * accessTokenService
	 */
	AccessTokenService accessTokenService;

	/**
	 * init
	 * 
	 * @param filterConfig
	 * @throws ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		configUtils = (ConfigUtils) springContext.getBean("configUtils");
		responseConverterUtil = (ResponseConverterUtil) springContext.getBean("responseConverterUtil");
		authorizationHeaderParser = (AuthorizationHeaderParser) springContext.getBean("authorizationHeaderParser");
		accessTokenService = (AccessTokenService) springContext.getBean("accessTokenServiceImpl");
		userService = (UserService) springContext.getBean("userServiceImpl");
		// oAuthHttp2 = new OAuthHttpImpl();
		oAuthHttp1 = new org.sarah.web.client.oauthclient1.http.impl.OAuthHttpImpl();
	}

	/**
	 * doFilter
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		try {
			AccessTokens accessTokens = checkSession(request, response);

			if (accessTokens == null) {
				if (request.getRequestURI().toString().equals("/")) {
					filterChain.doFilter(servletRequest, servletResponse);
				} else {
					authorize(request, response);
				}
			} else {
				if (request.getRequestURI().toString().contains("login")
						|| request.getRequestURI().toString().equals("/")) {
					sendToDashboard(request, response, accessTokens);
				} else if (request.getRequestURI().toString().startsWith("/api")) {
					filterChain.doFilter(servletRequest, servletResponse);
				} else {
					String requestUri = request.getRequestURI();
					if (accessTokens.getRole().equals("user")) {
						if (!requestUri.startsWith("/dashboard/user")) {
							sendToDashboard(request, response, accessTokens);
						} else {
							filterChain.doFilter(servletRequest, servletResponse);
						}
					} else if (accessTokens.getRole().equals("admin")) {
						if (!requestUri.startsWith("/dashboard/admin")) {
							sendToDashboard(request, response, accessTokens);
						} else {
							filterChain.doFilter(servletRequest, servletResponse);
						}
					} else if (accessTokens.getRole().equals("employee")) {
						if (!requestUri.startsWith("/dashboard/employee")) {
							sendToDashboard(request, response, accessTokens);
						} else {
							filterChain.doFilter(servletRequest, servletResponse);
						}
					} else {
						authorize(request, response);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * sendToDashboard
	 * 
	 * @param request
	 * @param response
	 * @param accessToken
	 * @throws Exception
	 */
	public void sendToDashboard(HttpServletRequest request, HttpServletResponse response, AccessTokens accessToken)
			throws Exception {
		response.sendRedirect(configUtils.getoAuthCallbackUrl() + "/" + accessToken.getRole());
	}

	/**
	 * destroy
	 */
	public void destroy() {
	}

	/**
	 * checkSession
	 * 
	 * @param request
	 * @param response
	 * @return AccessTokens
	 * @throws Exception
	 */
	AccessTokens checkSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String token = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals(SEMANTIC_ACCESS_TOKEN)) {
					token = cookie.getValue();
					break;
				}
			}
			if (token != null) {
				List<AccessTokens> list = accessTokenService.findByAccessToken(token);
				if (list != null && list.size() > 0) {
					return list.get(0);
				}
			}
		}
		return null;
	}

	/**
	 * authorize
	 * 
	 * @param request
	 * @return response
	 * @throws Exception
	 */
	void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getParameter(OAUTH_VERIFIER) != null) {
			oAuthverifier(request, response);
			return;
		} else {
			requestToken(request, response);
			return;
		}
	}

	/**
	 * requestToken
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	void requestToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestToken requestToken = oAuthHttp1.getOauthTokenAndSecret(configUtils.getoAuthBaseUrl(),
				configUtils.getoAuthCallbackUrl(), configUtils.getConsumerKey(), configUtils.getConsumerSecret());
		if (requestToken != null && requestToken.getOauth_token() != null) {
			response.sendRedirect("http://" + configUtils.getoAuthBaseUrl() + "/authenticate?oauth_token="
					+ requestToken.getOauth_token());
		} else {
			throw new RuntimeException("Sorry,Invalid OAuth Request");
		}
	}

	/**
	 * oAuthverifier
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	void oAuthverifier(HttpServletRequest request, HttpServletResponse response) throws Exception {
		AccessToken token = oAuthHttp1.getAccessToken(configUtils.getoAuthBaseUrl(),
				request.getParameter("oauth_token"), request.getParameter("oauth_verifier"),
				configUtils.getConsumerKey(), configUtils.getConsumerSecret());
		request.getSession(true);
		AccessTokens accessToken = null;
		User user = null;
		if (token != null && token.getOauth_token() != null && CODE_OK.equals(token.getCode())) {
			accessToken = new AccessTokens(token.getOauth_token(), token.getOauth_token_secret(), token.getUser_id());
			Cookie loginCookie = new Cookie(SEMANTIC_ACCESS_TOKEN, token.getOauth_token());
			loginCookie.setDomain(configUtils.getCookieDomainPath());
			loginCookie.setPath(COOKIE_PATH);

			Cookie resourceUserId = new Cookie(SEMANTIC_USER_ID, token.getUser_id());
			System.out.println("Cookie Domain Path"+configUtils.getCookieDomainPath());
			resourceUserId.setDomain(configUtils.getCookieDomainPath());
			resourceUserId.setPath(COOKIE_PATH);
			if (request.getParameter("rememberMe") != null) {
				loginCookie.setMaxAge(Integer.parseInt(configUtils.getCookieExpiryTimeInSeconds()));
				resourceUserId.setMaxAge(Integer.parseInt(configUtils.getCookieExpiryTimeInSeconds()));
			}

			response.addCookie(loginCookie);
			response.addCookie(resourceUserId);

			user = userService.createLocalAccount(accessToken);
			accessToken.setRole(user.getRole());
			accessTokenService.deleteByUserId(token.getUser_id());
			accessTokenService.save(accessToken);
		}
		// redirect to dashboard
		response.sendRedirect(configUtils.getoAuthCallbackUrl() + "/" + user.getRole());
	}

	// Utility Methods for OAuthentication
	String appendQueryString(HttpServletRequest request) throws Exception {
		Set<String> querySet = new HashSet<String>(Collections.list(request.getParameterNames()));
		StringBuilder url = new StringBuilder(request.getRequestURL().toString());
		boolean count = false;
		if (querySet != null) {
			Iterator<String> itr = querySet.iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				if (!key.equals(OAUTH_TOKEN) && !key.equals(OAUTH_VERIFIER)) {
					if (!count) {
						url.append("?").append(key).append("=").append(request.getParameter(key));
					} else {
						url.append("&").append(key).append("=").append(request.getParameter(key));
					}
					count = true;
				}
			}
		}
		return url.toString();
	}

}
