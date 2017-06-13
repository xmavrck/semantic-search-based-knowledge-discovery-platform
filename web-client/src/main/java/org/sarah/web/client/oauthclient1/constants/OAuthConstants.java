package org.sarah.web.client.oauthclient1.constants;

/**
 * The interface OAuthConstants.
 * 
 * @author chandan
 */
public interface OAuthConstants {
	/**
	 * The interface Url_OAuth1.
	 * 
	 */
	public interface Url_OAuth1 {
		String CHECK_ACCESS_TOKEN_PATH = "/check_access_token";
		String ACCESS_TOKEN_PATH = "/access_token";
		String REQUEST_TOKEN_PATH = "/request_token";
	}

	/**
	 * The interface Url_OAuth2.
	 * 
	 */
	public interface Url_OAuth2 {
		String AUTHORIZATION_PATH = "/authorization";
		String LINKEDIN_ACCESS_TOKEN_PATH = "/accessToken";
		String ACCESS_TOKEN_PATH = "/access_token";
	}

	/**
	 * The interface Vars.
	 * 
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
