package org.sarah.web.client;

import java.io.IOException;

import org.sarah.web.client.filter.AuthenticationFilter;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The class Application.
 * 
 * @author chandan
 */
@SpringBootApplication
@EnableAsync
@ServletComponentScan(basePackageClasses = { AuthenticationFilter.class })
public class Application {
	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (System.getenv("SEMANTIC_ANALYSIS_WEB_CLIENT_CONFIG") == null) {
				System.err.println("Please add SEMANTIC_ANALYSIS_WEB_CLIENT_CONFIG environment variable");
			} else {
				SpringApplication.run(Application.class, args);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		ppc.setLocations(new FileSystemResource(System.getenv("SEMANTIC_ANALYSIS_WEB_CLIENT_CONFIG")));
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
}
