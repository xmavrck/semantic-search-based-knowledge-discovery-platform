package com.sarah.semantic_analysis.web_oauth_server.controller.oauth;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.User;
import com.sarah.semantic_analysis.web_oauth_server.entities.ClientApp;
import com.sarah.semantic_analysis.web_oauth_server.entities.OAuthTemp;
import com.sarah.semantic_analysis.web_oauth_server.entities.TrustedApps;
import com.sarah.semantic_analysis.web_oauth_server.service.AccessTokenService;
import com.sarah.semantic_analysis.web_oauth_server.service.ConsumerService;
import com.sarah.semantic_analysis.web_oauth_server.service.TempTokenService;
import com.sarah.semantic_analysis.web_oauth_server.service.TrustedAppService;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.OAuthUtil;

/**
 * The class AuthenticationController.
 * 
 * @author chandan
 */
@Controller
public class AuthenticationController implements Constants {

	/**
	 * propertiesFile
	 */
	@Autowired
	ConfigUtils propertiesFile;
	/**
	 * authorisationService
	 */
	@Autowired
	public UserService authorisationService;
	/**
	 * accessTokenService
	 */
	@Autowired
	public AccessTokenService accessTokenService;
	/**
	 * tempTokenService
	 */
	@Autowired
	public TempTokenService tempTokenService;
	/**
	 * oAuthUtil
	 */
	@Autowired
	public OAuthUtil oAuthUtil;
	/**
	 * consumerService
	 */
	@Autowired
	public ConsumerService consumerService;
	/**
	 * trustedAppsSerice
	 */
	@Autowired
	public TrustedAppService trustedAppsSerice;
	/**
	 * COOKIE_TIME_INTERVAL
	 */
	Integer COOKIE_TIME_INTERVAL = 365 * 24 * 60 * 60;

	/**
	 * allowAccess
	 * 
	 * @param mm
	 * @param session
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.ALLOW_ACCESS)
	public String allowAccess(ModelMap mm, HttpSession session) {
		String appName = null;
		if (session.getAttribute("appName") != null) {
			appName = String.valueOf(session.getAttribute("appName"));
		}
		if (appName == null)
			appName = "This App";
		mm.addAttribute("data", appName + " wants to access your data ?");
		return "allowaccess";
	}

	/**
	 * saveAllowAccess
	 * 
	 * @param mm
	 * @param pageable
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = Constants.Controllers.ALLOW_ACCESS)
	public String saveAllowAccess(ModelMap mm, HttpSession session) {
		Map RESPONSE = new HashMap();
		try {
			TrustedApps ta = new TrustedApps();
			ta.setClientId(session.getAttribute("consumerKey") + "");
			trustedAppsSerice.saveOrUpdate(ta);
			if (session.getAttribute("callBackUrl") != null) {
				return String.valueOf(session.getAttribute("callBackUrl"));
			} else {
				if (session.getAttribute("code") != null) {
					RESPONSE.put("code", session.getAttribute("code") + "");
					RESPONSE.put("status", session.getAttribute("error") + "");
				} else if (session.getAttribute("oauth_verifier") != null) {
					RESPONSE.put("oauth_verifier", session.getAttribute("oauth_verifier") + "");
					RESPONSE.put("oauth_token", session.getAttribute("oauth_token") + "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		RESPONSE.put("code", 400);
		RESPONSE.put("status", "Bad Request");
		mm.addAttribute("data", oAuthUtil.convertMapToNameValuePair(RESPONSE));
		return "authenticate";
	}

	/**
	 * disAllowAccess
	 * 
	 * @param mm
	 * @param session
	 * @param response
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = Constants.Controllers.DISALLOW_ACCESS)
	public String disAllowAccess(ModelMap mm, HttpSession session, HttpServletResponse response) {
		response.setStatus(401);
		return "401";
	}

	/**
	 * authenticate
	 * 
	 * @param oauth_token
	 * @param session
	 * @param mm
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = Constants.Controllers.AUTHENTICATE)
	public String authenticate(@RequestParam(required = false, value = "oauth_token") String oauth_token,
			HttpSession session, ModelMap mm) {
		if (oauth_token != null) {
			mm.addAttribute("oauth_token", oauth_token);
			if (session.getAttribute("autherror") != null) {
				mm.addAttribute("error", String.valueOf(session.getAttribute("autherror")));
				session.removeAttribute("autherror");
			}
			if (session.getAttribute("user") != null) {
				mm.addAttribute("user", (User) session.getAttribute("user"));
				session.removeAttribute("user");
			}
			session.setAttribute("oauth_token", oauth_token);
		}
		return "authenticate";
	}

	/**
	 * postLoginForm
	 * 
	 * @param rememberMe
	 * @param user
	 * @param request
	 * @param response
	 * @param mm
	 * @param session
	 * 
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.POST, value = Constants.Controllers.AUTHENTICATE, produces = "application/x-www-form-urlencoded")
	public String postLoginForm(@RequestParam(value = "rememberMe", required = false) Integer rememberMe, User user,
			HttpServletRequest request, HttpServletResponse response, ModelMap mm, HttpSession session)
			throws Exception {
		boolean res = false;
		Map RESPONSE = new HashMap();
		if (user.getOauth_token() == null && session.getAttribute("oauth_token") != null) {
			user.setOauth_token(String.valueOf(session.getAttribute("oauth_token")));
		}
		response.addHeader("content-type", "application/x-www-form-urlencoded");
		try {
			if (user.getEmailId() != null && user.getPassword() != null) {
				com.sarah.semantic_analysis.web_oauth_server.entities.User userRes = authorisationService
						.findByEmailIdIgnoreCaseAndPassword(user.getEmailId(), user.getPassword());
				if (userRes != null && userRes.getId() != null) {
					if (userRes.getIsEnabled()) {
						String userId = userRes.getId();
						OAuthTemp oAuthTemp = tempTokenService.updateOAuthVerifier(user.getOauth_token(), userId);
						if (oAuthTemp != null) {
							String oAuth_verifier = oAuthTemp.getoAuthVerified();
							res = true;
							ClientApp clientApp = consumerService.findByConsumerKey(oAuthTemp.getConsumerKey());

							session.setAttribute("consumerKey", oAuthTemp.getConsumerKey());
							session.setAttribute("appName", clientApp.getAppName());

							String callBackUrl = tempTokenService.getCallBackUrl(user.getOauth_token());
							// TrustedApps tApps =
							// trustedAppsSerice.get(oAuthTemp.getConsumerKey());
							// boolean isTrustedApp = false;
							// if (tApps != null) {
							// isTrustedApp = true;
							// }
							if (callBackUrl == null || callBackUrl.equals("")) {
								if (clientApp.getCallBackUrl() != null) {
									callBackUrl = clientApp.getCallBackUrl();
								}
							}
							if (callBackUrl != null && !callBackUrl.equals("")) {
								if (oAuthUtil.getNameValuePairFromQueryString(new URL(callBackUrl)).size() > 0) {
									callBackUrl = "redirect:" + callBackUrl + "&oauth_token="
											+ URLEncoder.encode(user.getOauth_token(), "UTF-8") + "&oauth_verifier="
											+ URLEncoder.encode(oAuth_verifier, "UTF-8");
								} else {
									callBackUrl = "redirect:" + callBackUrl + "?oauth_token="
											+ URLEncoder.encode(user.getOauth_token(), "UTF-8") + "&oauth_verifier="
											+ URLEncoder.encode(oAuth_verifier, "UTF-8");
								}
								if (rememberMe != null && rememberMe == 1) {
									callBackUrl += "&rememberMe";
								}
								session.setAttribute("callBackUrl", callBackUrl);
								// if (isTrustedApp) {
								return session.getAttribute("callBackUrl") + "";
								// } else {
								// return "redirect:allowaccess";
								// }
							} else {
								session.setAttribute("oauth_token", user.getOauth_token());
								session.setAttribute("oauth_verifier", oAuth_verifier);
								RESPONSE.put("oauth_token", user.getOauth_token());
								RESPONSE.put("oauth_verifier", oAuth_verifier);
								// if (isTrustedApp) {
								return oAuthUtil.convertMapToNameValuePair(RESPONSE);
								// } else {
								// return "redirect:allowaccess";
								// }
							}
						} else {
							session.setAttribute("autherror", "Sorry,This is a invalid oauth token.");
						}
					} else {
						session.setAttribute("autherror",
								"Sorry,Your account is inactive yet.Please visit the activation link that has been sent to your emailId");
						RESPONSE.put("code", "404");
						RESPONSE.put("error",
								"Sorry,Your account is inactive.Please visit the activation link that has been sent to your emailId");
					}
				} else {
					session.setAttribute("autherror", "Invalid emailid or Password.");
					RESPONSE.put("code", "404");
					RESPONSE.put("error", "Sorry,Invalid username or Password");
				}
			} else {
				session.setAttribute("autherror", "Sorry,This is a invalid oauth token.");
				RESPONSE.put("code", "209");
				RESPONSE.put("error", "Sorry,Invalid OAuthToken");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("autherror", "Sorry,This is a bad request.Please try again later");
			RESPONSE.put("code", "400");
			RESPONSE.put("error", "Bad Request");
		}
		if (session.getAttribute("code") != null) {
			session.setAttribute("code", RESPONSE.get("code"));
			session.setAttribute("error", RESPONSE.get("error"));
		}
		session.setAttribute("user", user);
		return "redirect:/" + propertiesFile.getSemanticAnalysisOAuthBaseUrl() + "/authenticate?oauth_token="
				+ user.getOauth_token();
	}
}
