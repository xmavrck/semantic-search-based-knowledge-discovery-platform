package org.sarah.web.client.configuration;

import org.sarah.web.client.interceptor.TopMenuInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.mongodb.MongoClient;

/*
 * The class MvcConfiguration
 * 
 * @author Chandan
 * */
@Configuration
public class MvcConfiguration extends WebMvcAutoConfigurationAdapter {

	/**
	 * configureDefaultServletHandling
	 * 
	 * @param configurer
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/**
	 * CLASSPATH_RESOURCE_LOCATIONS
	 */
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };

	/**
	 * addResourceHandlers
	 * 
	 * @param ResourceHandlerRegistry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}

	/**
	 * topMenuInterceptor
	 * 
	 * @return TopMenuInterceptor
	 */
	@Bean
	public TopMenuInterceptor topMenuInterceptor() {
		return new TopMenuInterceptor();
	}

	/**
	 * addInterceptors
	 * 
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(topMenuInterceptor());
	}

	/**
	 * mongoTemplate
	 * 
	 * @param mongoDatabaseName
	 * @param mongoHostName
	 * @param mongoPortNo
	 * @return MongoTemplate
	 * @throws Exception
	 */
	@Bean
	public MongoTemplate mongoTemplate(@Value("${mongo.database}") String mongoDatabaseName,
			@Value("${mongo.hostname}") String mongoHostName, @Value("${mongo.port}") Integer mongoPortNo)
			throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(mongoHostName, mongoPortNo), mongoDatabaseName);
		return mongoTemplate;
	}

}
