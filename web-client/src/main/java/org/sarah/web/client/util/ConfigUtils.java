package org.sarah.web.client.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * The class ConfigUtils.
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

	@Value("${semantic.hostname}")
	private String semanticHostname;

	

	@Value("${cookieDomainPath}")
	private String cookieDomainPath;

	@Value("${cookieExpiryTimeInSeconds}")
	private String cookieExpiryTimeInSeconds;

	@Value("${oAuth.baseUrl}")
	private String oAuthBaseUrl;

	@Value("${consumerKey}")
	private String consumerKey;

	@Value("${consumerSecret}")
	private String consumerSecret;

	@Value("${oAuth.callbackUrl}")
	private String oAuthCallbackUrl;

	@Value("${oAuth.rest.baseUrl}")
	private String oAuthRestBaseUrl;

	
	public String getoAuthRestBaseUrl() {
		return oAuthRestBaseUrl;
	}

	public String getoAuthCallbackUrl() {
		return oAuthCallbackUrl;
	}

	public String getMongoDatabaseName() {
		return mongoDatabaseName;
	}

	public String getMongoHostName() {
		return mongoHostName;
	}

	public Integer getMongoPortNo() {
		return mongoPortNo;
	}

	public String getSemanticHostname() {
		return semanticHostname;
	}

	public String getCookieDomainPath() {
		return cookieDomainPath;
	}

	public String getCookieExpiryTimeInSeconds() {
		return cookieExpiryTimeInSeconds;
	}

	public String getoAuthBaseUrl() {
		return oAuthBaseUrl;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

}
