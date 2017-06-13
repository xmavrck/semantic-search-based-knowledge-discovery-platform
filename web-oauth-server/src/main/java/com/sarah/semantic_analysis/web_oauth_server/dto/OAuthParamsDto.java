package com.sarah.semantic_analysis.web_oauth_server.dto;

import java.util.HashMap;
/**
 * The class OAuthParamsDto.
 * 
 * @author chandan
 */
public class OAuthParamsDto {
	String consumerSecret;

	String consumerKey;
	String nonce;
	String signatureMethod;
	String signature;
	String timestamp;
	String method;

	String oAuthVersion;
	String oAuthToken;
	String oAuthTokenSecret;
	HashMap<String, String> formData;
	String url;

	public HashMap<String, String> getFormData() {
		return formData;
	}

	public void setFormData(HashMap<String, String> formData) {
		this.formData = formData;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * employees
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return String
	 * @throws Exception
	 */
	public OAuthParamsDto(String consumerKey, String nonce, String signatureMethod, String signature, String timestamp,
			String method, String oAuthVersion, String oAuthToken) {
		super();
		this.consumerKey = consumerKey;
		this.nonce = nonce;
		this.signatureMethod = signatureMethod;
		this.signature = signature;
		this.timestamp = timestamp;
		this.method = method;
		this.oAuthVersion = oAuthVersion;
		this.oAuthToken = oAuthToken;
	}
	/**
	 * employees
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return String
	 * @throws Exception
	 */
	public OAuthParamsDto(String consumerKey, String nonce, String signatureMethod, String signature, String timestamp,
			String method, String oAuthVersion, String oAuthToken, String url) {
		super();
		this.consumerKey = consumerKey;
		this.nonce = nonce;
		this.url = url;
		this.signatureMethod = signatureMethod;
		this.signature = signature;
		this.timestamp = timestamp;
		this.method = method;
		this.oAuthVersion = oAuthVersion;
		this.oAuthToken = oAuthToken;
	}
	/**
	 * employees
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return String
	 * @throws Exception
	 */
	public OAuthParamsDto(String consumerKey, String nonce, String signatureMethod, String signature, String timestamp,
			String method, String oAuthVersion, String oAuthToken, HashMap<String, String> formData, String url) {
		super();
		this.consumerKey = consumerKey;
		this.nonce = nonce;
		this.signatureMethod = signatureMethod;
		this.signature = signature;
		this.timestamp = timestamp;
		this.method = method;
		this.oAuthVersion = oAuthVersion;
		this.oAuthToken = oAuthToken;
		this.formData = formData;
		this.url = url;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public OAuthParamsDto() {
		super();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getoAuthTokenSecret() {
		return oAuthTokenSecret;
	}

	public void setoAuthTokenSecret(String oAuthTokenSecret) {
		this.oAuthTokenSecret = oAuthTokenSecret;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getSignatureMethod() {
		return signatureMethod;
	}

	public void setSignatureMethod(String signatureMethod) {
		this.signatureMethod = signatureMethod;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getoAuthVersion() {
		return oAuthVersion;
	}

	public void setoAuthVersion(String oAuthVersion) {
		this.oAuthVersion = oAuthVersion;
	}

	public String getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	@Override
	public String toString() {
		return consumerSecret + "   " + consumerKey + "   " + nonce + "   " + signatureMethod + "   " + timestamp
				+ "   " + method + "   " + oAuthVersion + "   " + oAuthToken + "   " + oAuthTokenSecret;
	}
}
