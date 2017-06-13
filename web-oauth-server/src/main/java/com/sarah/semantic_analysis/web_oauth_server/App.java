package com.sarah.semantic_analysis.web_oauth_server;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
/**
 * The class App.
 * 
 * @author chandan
 */
@SpringBootApplication
public class App implements Constants {
	
	/**
	 * main
	 * 
	 * @param args
	 */	
	public static void main(String[] args) {
		if (System.getenv(SEMANTIC_ANALYSIS_OAUTH_CONFIG) == null) {
			System.err.println("Please add SEMANTIC_ANALYSIS_OAUTH_CONFIG variable");
		} else {
			SpringApplication.run(App.class, args);
		}
	}
	/**
	 * propertyPlaceholderConfigurer
	 * 
	 * @return PropertyPlaceholderConfigurer
	 * @throws IOException
	 */	
	@Bean
	public PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() throws IOException {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocations(new Resource[] { new FileSystemResource(System.getenv("SEMANTIC_ANALYSIS_OAUTH_CONFIG")) });
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
}
