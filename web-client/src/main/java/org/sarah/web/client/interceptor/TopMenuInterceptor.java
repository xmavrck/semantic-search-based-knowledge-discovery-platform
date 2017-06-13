package org.sarah.web.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sarah.web.client.constants.Constants;
import org.sarah.web.client.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * The class TopMenuInterceptor.
 * 
 * @author chandan
 */
public class TopMenuInterceptor extends HandlerInterceptorAdapter implements Constants.App {
	/**
	 * propertiesFile
	 */
	@Autowired
	ConfigUtils propertiesFile;

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

		if (modelAndView != null) {
			modelAndView.addObject(OAUTH_URL, propertiesFile.getoAuthBaseUrl());
		}
	}
}
