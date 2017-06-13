package com.sarah.semantic_analysis.web_oauth_server.interceptors;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * The class CsrfInterceptor.
 * 
 * @author chandan
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {
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
		String token = UUID.randomUUID().toString();
		if (request.getMethod().toUpperCase().equals("GET")) {
			if (modelAndView != null) {
				modelAndView.addObject("csrfToken", token);
				request.getSession().setAttribute("csrfToken", token);
			}
		} else if (request.getMethod().toUpperCase().equals("POST")) {
			
		}
	}

}
