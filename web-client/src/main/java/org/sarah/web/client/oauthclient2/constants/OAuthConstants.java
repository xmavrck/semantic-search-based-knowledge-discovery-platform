package org.sarah.web.client.oauthclient2.constants;

/**
 * The class MongoConfiguration.
 * @author chandan
 */
public interface OAuthConstants {
	/**
	 * The class MongoConfiguration.
	 * @author chandan
	 */
	public interface Url_OAuth1 {
		String CHECK_ACCESS_TOKEN_PATH = "/check_access_token";
		String ACCESS_TOKEN_PATH = "/access_token";
		String REQUEST_TOKEN_PATH = "/request_token";
	}
	/**
	 * The class MongoConfiguration.
	 * @author chandan
	 */
	public interface Url_OAuth2 {
		String AUTHORIZATION_PATH = "/authorization";
		String LINKEDIN_ACCESS_TOKEN_PATH = "/accessToken";
		String ACCESS_TOKEN_PATH = "/access_token";
	}
	/**
	 * The class MongoConfiguration.
	 * @author chandan
	 */
	public interface Vars {
		// key used in SignatureUtils
		String HMAC_SHA1 = "HmacSHA1";
		// signature method used in OAuthHttp
		String SIGNATURE_METHOD = "HMAC-SHA1";
		// version of oauth being used
		String OAUTH_VERSION = "1.0";
		// encoding scheme used to read response and also while generating
		// signature
		String ENC = "UTF-8";
		// protocol used to call authentication api
		String PROTOCOL = "http";
		// buffer size while reading response
		short BUFFER_SIZE = 2048;
	}
}
