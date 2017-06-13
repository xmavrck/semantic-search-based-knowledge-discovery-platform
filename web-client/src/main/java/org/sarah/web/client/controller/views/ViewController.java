package org.sarah.web.client.controller.views;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.sarah.web.client.constants.Constants;
import org.sarah.web.client.oauthclient1.constants.OAuthConstants;
import org.sarah.web.client.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The class ViewController.
 * 
 * @author chandan
 */
@Controller
public class ViewController implements Constants, Constants.Authentication, Constants.App, Constants.OAuthProvider,
		OAuthConstants.Url_OAuth2 {
	/**
	 * configUtils
	 */
	@Autowired
	private ConfigUtils configUtils;

	/**
	 * index
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
	/**
	 * index
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/about-us", method = RequestMethod.GET)
	public String aboutus() {
		return "aboutus";
	}
	/**
	 * logout
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(SEMANTIC_ACCESS_TOKEN)) {
						Cookie modelfactoryCookie = new Cookie(SEMANTIC_ACCESS_TOKEN, null);
						modelfactoryCookie.setDomain(configUtils.getCookieDomainPath());
						modelfactoryCookie.setPath("/");
						modelfactoryCookie.setMaxAge(0);
						response.addCookie(modelfactoryCookie);
					}
					if (cookie.getName().equals(SEMANTIC_USER_ID)) {
						Cookie modelfactoryCookie = new Cookie(SEMANTIC_USER_ID, null);
						modelfactoryCookie.setDomain(configUtils.getCookieDomainPath());
						modelfactoryCookie.setPath("/");
						modelfactoryCookie.setMaxAge(0);
						response.addCookie(modelfactoryCookie);
					}
				}
			}
			session.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

}
