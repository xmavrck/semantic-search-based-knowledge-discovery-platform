package com.sarah.web_crawler.config;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;

/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
@Configuration
public class MvcConfiguration extends WebMvcAutoConfigurationAdapter {

	/**
	 * configureDefaultServletHandling.
	 * 
	 * @param configurer
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
