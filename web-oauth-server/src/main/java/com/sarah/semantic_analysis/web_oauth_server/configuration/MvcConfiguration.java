package com.sarah.semantic_analysis.web_oauth_server.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.mongodb.MongoClient;
import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.interceptors.CsrfInterceptor;
import com.sarah.semantic_analysis.web_oauth_server.interceptors.LoginInterceptor;


/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
@Configuration
public class MvcConfiguration extends WebMvcAutoConfigurationAdapter implements Constants.Controllers {
	
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
	 * getMailProperties.
	 * 
	 * @return Properties
	 */
	@Bean
	public Properties getMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		return properties;
	}
	/**
	 * The CLASSPATH_RESOURCE_LOCATIONS for static data to be served
	 */
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
	
	/**
	 * addResourceHandlers.
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}
	/**
	 * javaMailSender.
	 * @param emailId
	 * @param password
	 * @param emailHostName
	 * @param emailPortNo
	 * @return JavaMailSender
	 */
	@Bean
	public JavaMailSender javaMailSender(@Value("${semanticanalysis.oauth.emailId}") String emailId,
			@Value("${semanticanalysis.oauth.password}") String password,
			@Value("${semanticanalysis.oauth.emailHostName}") String emailHostName,
			@Value("${semanticanalysis.oauth.emailPortNo}") Integer emailPortNo) {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		try {
			javaMailSender.setHost(emailHostName);
			javaMailSender.setPort(emailPortNo);
			javaMailSender.setUsername(emailId);
			javaMailSender.setPassword(password);
			javaMailSender.setJavaMailProperties(getMailProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return javaMailSender;
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
	 * loginInterceptor.
	 * 
	 * @return LoginInterceptor
	 */
	@Bean
	public LoginInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}

	/**
	 * csrfInterceptor.
	 * 
	 * @return CsrfInterceptor
	 */
	@Bean
	public CsrfInterceptor csrfInterceptor() {
		return new CsrfInterceptor();
	}

	/**
	 * addInterceptors.
	 * 
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor());
		registry.addInterceptor(csrfInterceptor()).addPathPatterns(REGISTER_APP, EDIT_APP);
	}

}
