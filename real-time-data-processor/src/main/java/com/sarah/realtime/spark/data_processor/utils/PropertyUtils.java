package com.sarah.realtime.spark.data_processor.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/*
 * The class PropertyUtils
 * 
 * @author Chandan
 * */
public class PropertyUtils implements Serializable {

	/**
	 * props
	 */
	Properties props = new Properties();

	/**
	 * PropertyUtils
	 * 
	 * @throws Exception
	 */
	public PropertyUtils() throws Exception {
		InputStream inputStream = new FileInputStream(System.getenv("SARAH_SPARK_STREAMING_CONFIG"));
		props.load(inputStream);
	}

	/**
	 * getMongoHost
	 * 
	 * @return String
	 */
	public String getMongoHost() {
		return props.getProperty("mongo.hostname");
	}

	/**
	 * getMongoPort
	 * 
	 * @return String
	 */
	public String getMongoPort() {
		return props.getProperty("mongo.port");
	}

	/**
	 * getMongoDatabase
	 * 
	 * @return String
	 */
	public String getMongoDatabase() {
		return props.getProperty("mongo.database");
	}

	/**
	 * getCrawlerHost
	 * 
	 * @return String
	 */
	public String getCrawlerHost() {
		return props.getProperty("crawler.api.host");
	}
	
	/**
	 * getCrawlerPort
	 * 
	 * @return String
	 */
	public String getCrawlerPort() {
		return props.getProperty("crawler.api.port");
	}
	
	/**
	 * getFusekiHost
	 * 
	 * @return String
	 */
	public String getFusekiHost() {
		return props.getProperty("fuseki.host");
	}

	/**
	 * getNLPAPIPort
	 * 
	 * @return String
	 */
	public String getNLPPort() {
		return props.getProperty("nlp.api.port");
	}

	/**
	 * getNLPAPIHost
	 * 
	 * @return String
	 */
	public String getNLPHost() {
		return props.getProperty("nlp.api.host");
	}

	/**
	 * getFusekiPort
	 * 
	 * @return String
	 */
	public String getFusekiPort() {
		return props.getProperty("fuseki.port");
	}

	/**
	 * getMongoPort
	 * 
	 * @return String
	 */
	public String getNameFieldName() {
		return props.getProperty("name.fieldName");
	}

	/**
	 * getFusekiDataset
	 * 
	 * @return String
	 */
	public String getFusekiDataset() {
		return props.getProperty("fuseki.dataset");
	}

	/**
	 * getFusekiUsername
	 * 
	 * @return String
	 */
	public String getFusekiUsername() {
		return props.getProperty("fuseki.username");
	}

	/**
	 * getFusekiPassword
	 * 
	 * @return String
	 */
	public String getFusekiPassword() {
		return props.getProperty("fuseki.password");
	}

	/**
	 * getClassificationMlibHost
	 * 
	 * @return String
	 */
	public String getClassificationMlibHost() {
		return props.getProperty("classification.mlib.host");
	}

	/**
	 * getRubyTemppath
	 * 
	 * @return String
	 */
	public String getRubyTemppath() {
		return props.getProperty("ruby.temppath");
	}

	/**
	 * getClassificationMlibPort
	 * 
	 * @return String
	 */
	public String getClassificationMlibPort() {
		return props.getProperty("classification.mlib.port");
	}

	/**
	 * getSparkJobName
	 * 
	 * @return String
	 */
	public String getSparkJobName() {
		return props.getProperty("spark.job.name");
	}

	/**
	 * getSparkStreamingInterval
	 * 
	 * @return String
	 */
	public String getSparkStreamingInterval() {
		return props.getProperty("spark.streaming.interval");
	}

	/**
	 * getHdfsUsername
	 * 
	 * @return String
	 */
	public String getHdfsUsername() {
		return props.getProperty("hdfs.username");
	}

	/**
	 * getKafkaTopic
	 * 
	 * @return String
	 */
	public String getKafkaTopic() {
		return props.getProperty("kafka.topic");
	}

	/**
	 * getMetadataBrokerList
	 * 
	 * @return String
	 */
	public String getMetadataBrokerList() {
		return props.getProperty("metadata.broker.list");
	}

	/**
	 * getOntologyUri
	 * 
	 * @return String
	 */
	public String getOntologyUri() {
		return props.getProperty("ontology.uri");
	}

	/**
	 * getUrlFieldName
	 * 
	 * @return String
	 */
	public String getUrlFieldName() {
		return props.getProperty("url.fieldName");
	}

	/**
	 * getKeywordsFieldName
	 * 
	 * @return String
	 */
	public String getKeywordsFieldName() {
		return props.getProperty("keywords.fieldName");
	}

	/**
	 * getEmailFieldName
	 * 
	 * @return String
	 */
	public String getEmailFieldName() {
		return props.getProperty("email.fieldName");
	}

	/**
	 * getUniversityFieldName
	 * 
	 * @return String
	 */
	public String getUniversityFieldName() {
		return props.getProperty("university.fieldName");
	}

	/**
	 * getResearchAreasFieldName
	 * 
	 * @return String
	 */
	public String getResearchAreasFieldName() {
		return props.getProperty("researchAreas.fieldName");
	}

	/**
	 * getPublicationsFieldName
	 * 
	 * @return String
	 */
	public String getPublicationsFieldName() {
		return props.getProperty("publications.fieldName");
	}

}
