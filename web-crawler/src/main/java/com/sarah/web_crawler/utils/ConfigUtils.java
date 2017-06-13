package com.sarah.web_crawler.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The class ConfigUtils
 * 
 * @author chandan
 */
@Component
public class ConfigUtils {
	@Value("${mongo.database}")
	private String mongoDatabaseName;

	@Value("${mongo.hostname}")
	private String mongoHostName;

	@Value("${mongo.port}")
	private Integer mongoPortNo;

	@Value("${proxy.server.hostname}")
	private String proxyServerHostname;

	@Value("${proxy.enabled}")
	private String proxyEnabled;

	@Value("${proxy.server.port}")
	private Integer proxyServerPort;

	@Value("${zip.files.tempath}")
	private String zipFilesTempath;

	@Value("${hadoop.username}")
	private String hadoopUsername;

	@Value("${hdfs.rootDir}")
	private String hdfsRootDir;

	@Value("${hdfs.uri}")
	private String hadoopUri;

	@Value("${hdfs.directory.upload.rawfile}")
	private String hdfsDirectoryUploadRawFile;

	@Value("${kafka.producer.config}")
	private String kafkaProducerConfig;

	@Value("${webcrawler.http.timeout}")
	private Integer webcrawlerHttpTimeout;

	

	@Value("${fuseki.host}")
	private String fusekiHost;

	@Value("${fuseki.port}")
	private Integer fusekiPort;

	@Value("${fuseki.dataset}")
	private String fusekiDataSet;

	@Value("${fuseki.username}")
	private String fusekiUsername;

	@Value("${fuseki.password}")
	private String fusekiPassword;

	@Value("${python.host}")
	private String pythonHost;

	@Value("${python.port}")
	private Integer pythonPort;
	


	@Value("${publications.fieldName}")
	private String publicationsFieldName;

	@Value("${researchAreas.fieldName}")
	private String researchAreasFieldName;

	@Value("${university.fieldName}")
	private String universityFieldName;


	@Value("${email.fieldName}")
	private String emailFieldName;

	@Value("${keywords.fieldName}")
	private String keywordsFieldName;

	@Value("${url.fieldName}")
	private String urlFieldName;


	@Value("${ontology.uri}")
	private String ontologyUri;


	public String getPublicationsFieldName() {
		return publicationsFieldName;
	}
	public String getResearchAreasFieldName() {
		return researchAreasFieldName;
	}
	public String getUniversityFieldName() {
		return universityFieldName;
	}
	public String getEmailFieldName() {
		return emailFieldName;
	}
	public String getKeywordsFieldName() {
		return keywordsFieldName;
	}
	public String getUrlFieldName() {
		return urlFieldName;
	}
	public String getOntologyUri() {
		return ontologyUri;
	}
	public String getPythonHost() {
		return pythonHost;
	}
	public Integer getPythonPort() {
		return pythonPort;
	}
	
	public String getFusekiHost() {
		return fusekiHost;
	}

	public Integer getFusekiPort() {
		return fusekiPort;
	}

	public String getFusekiDataSet() {
		return fusekiDataSet;
	}

	public String getFusekiPassword() {
		return fusekiPassword;
	}

	public String getFusekiUsername() {
		return fusekiUsername;
	}

	

	public Integer getWebcrawlerHttpTimeout() {
		return webcrawlerHttpTimeout;
	}

	public String getHdfsRootDir() {
		return hdfsRootDir;
	}

	public String getHadoopUri() {
		return hadoopUri;
	}

	public String getHadoopUsername() {
		return hadoopUsername;
	}

	public String getKafkaProducerConfig() {
		return kafkaProducerConfig;
	}

	public String getHdfsDirectoryUploadRawFile() {
		return hdfsDirectoryUploadRawFile;
	}

	public String getZipFilesTempath() {
		return zipFilesTempath;
	}

	public boolean getProxyEnabled() {
		return Boolean.parseBoolean(proxyEnabled);
	}

	public String getMongoDatabaseName() {
		return mongoDatabaseName;
	}

	public String getProxyServerHostname() {
		return proxyServerHostname;
	}

	public Integer getProxyServerPort() {
		return proxyServerPort;
	}

	public String getMongoHostName() {
		return mongoHostName;
	}

	public Integer getMongoPortNo() {
		return mongoPortNo;
	}

}
