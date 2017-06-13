package com.sarah.web_crawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;

import com.mongodb.MongoClient;

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

	/**
	 * mongoTemplate.
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
		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(mongoHostName), mongoDatabaseName);
		return mongoTemplate;
	}

	/**
	 * multipartResolver.
	 * 
	 * @param mongoDatabaseName
	 * @param maxInMemorySize
	 * @return CommonsMultipartResolver
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver(@Value("${multipart.maxInMemorySize}") int maxInMemorySize) {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxInMemorySize(maxInMemorySize);
		resolver.setMaxUploadSize(maxInMemorySize);
		return resolver;
	}

	/**
	 * multipartFilter.
	 * 
	 * @param multipartFilter
	 * @return MultipartFilter
	 */
	@Bean
	@Order(0)
	public MultipartFilter multipartFilter() {
		MultipartFilter multipartFilter = new MultipartFilter();
		multipartFilter.setMultipartResolverBeanName("multipartResolver");
		return multipartFilter;
	}
}
