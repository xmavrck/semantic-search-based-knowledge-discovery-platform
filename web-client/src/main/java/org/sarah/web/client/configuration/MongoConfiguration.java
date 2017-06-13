package org.sarah.web.client.configuration;

import org.sarah.web.client.util.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
/*
 * The class MongoConfiguration
 * 
 * @author Chandan
 * */
@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

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
	 * @return String 
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
		return "org.sarah.web.client.entities";
	}
	
}
