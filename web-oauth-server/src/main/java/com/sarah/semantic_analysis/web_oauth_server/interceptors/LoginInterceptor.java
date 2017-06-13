package com.sarah.semantic_analysis.web_oauth_server.interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;

/**
 * The class LoginInterceptor.
 * 
 * @author chandan
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * propertiesFile
	 */
	@Autowired
	ConfigUtils propertiesFile;
	/**
	 * IGNOREURLS
	 */
	String[] IGNOREURLS = { "logout", "verify", "accountactivation", "delete-app" };

	/**
	 * postHandle
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Cookie[] cookies = request.getCookies();
		boolean isLoggedIn = false;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cloudfactorylogin")) {
					isLoggedIn = true;
					break;
				}
			}
		}
		String path = request.getRequestURI();
		if (modelAndView != null) {
			if (!request.getMethod().toUpperCase().equals("POST") && path != null && !path.trim().equals("/")
					&& !isIgnoredUrl(path)) {
				if (isLoggedIn) {
					modelAndView.addObject("isLoggedIn", true);
					modelAndView.addObject("link", propertiesFile.getSemanticAnalysisOauthHostname() + "/"
							+ propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/logout");
					modelAndView.addObject("linkTitle", "Logout");
				} else {
					modelAndView.addObject("isLoggedIn", false);
					modelAndView.addObject("link", propertiesFile.getSemanticAnalysisOauthHostname() + "/"
							+ propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/signup");
					modelAndView.addObject("linkTitle", "Sign Up");
				}
				modelAndView.addObject("semanticAnalysisHost", propertiesFile.getSemanticAnalysisHostname());
				modelAndView.addObject("semanticAnalysisOAuth", propertiesFile.getSemanticAnalysisOauthHostname() + "/"
						+ propertiesFile.getSemanticAnalysisOAuthBaseUrl());
			}
		}
	}

	/**
	 * isIgnoredUrl
	 * 
	 * @param path
	 * @return boolean
	 */
	boolean isIgnoredUrl(String path) {
		for (String url : IGNOREURLS) {
			if (path.equals("/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/" + url)) {
				return true;
			}
		}
		return false;
	}
}
