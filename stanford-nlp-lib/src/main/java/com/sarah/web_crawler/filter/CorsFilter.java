package com.sarah.web_crawler.filter;

import javax.servlet.annotation.WebFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

@WebFilter(urlPatterns = { "/*" })
public class CorsFilter implements Filter {
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		try {
			if (req.getMethod().equals("OPTIONS") || req.getMethod().equals("options")) {
				addCorsHeaders(true,response);
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				return;
			}
			if (req.getMethod().equals("PUT") || req.getMethod().equals("PUT")) {
				addCorsHeaders(true,response);
				filterChain.doFilter(arg0, arg1);
			}else{
				filterChain.doFilter(arg0, arg1);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	void addCorsHeaders(boolean withCors, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT,PATCH");
		response.setHeader("Access-Control-Max-Age", "36000000");
		response.setHeader("Access-Control-Allow-Headers",
				"X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
