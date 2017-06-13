package com.sarah.web_crawler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.sarah.web_crawler.constants.Constants;
import com.sarah.web_crawler.utils.ConfigUtils;

/**
 * The class MongoConfiguration.
 * @author chandan
 */
@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration implements Constants {

	/**
	 * config utils
	 */
	@Autowired
	ConfigUtils configUtils;
	
	/**
	 * mongo.
	 * @return the Mongo 
	 * @throws Exception
	 */
	@Bean
	public Mongo mongo() throws Exception {
		return new Mongo(configUtils.getMongoHostName(), configUtils.getMongoPortNo());
	}
	
	/**
	 * getDataBase
	 * @return the String 
	 */
	@Override
	public String getDatabaseName() {
		return configUtils.getMongoDatabaseName();
	}

	/**
	 * getMappingBasePackage
	 * @return the String 
	 */
	@Override
	public String getMappingBasePackage() {
		return BASE_PACKAGE_NAME;
	}
}
