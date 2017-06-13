package org.sarah.web.client.oauthclient1.types;

import java.util.HashMap;
/**
 * The class OAuthRequest.
 * @author chandan
 */
public class OAuthRequest {
	String userId,  oAuthToken, consumerKey, consumerSecret, accessTokenSecret;
	String nonce, timestamp, signaturemethod, signature, oauthversion;
	HashMap<String, String> formData;
	
	public OAuthRequest() {
	}
	
	public OAuthRequest(String userId,  String oAuthToken, String consumerKey, String consumerSecret,
			String accessTokenSecret, String nonce, String timestamp, String signaturemethod, String signature,
			String oauthversion, HashMap<String, String> formData) {
		super();
		this.userId = userId;
		this.oAuthToken = oAuthToken;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessTokenSecret = accessTokenSecret;
		this.nonce = nonce;
		this.timestamp = timestamp;
		this.signaturemethod = signaturemethod;
		this.signature = signature;
		this.oauthversion = oauthversion;
		this.formData = formData;
	}

	public OAuthRequest(String userId, String oAuthToken, String consumerKey, String consumerSecret,
			String accessTokenSecret) {
		this.userId = userId;
		this.oAuthToken = oAuthToken;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignaturemethod() {
		return signaturemethod;
	}

	public void setSignaturemethod(String signaturemethod) {
		this.signaturemethod = signaturemethod;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getOauthversion() {
		return oauthversion;
	}

	public void setOauthversion(String oauthversion) {
		this.oauthversion = oauthversion;
	}

	public HashMap<String, String> getFormData() {
		return formData;
	}

	public void setFormData(HashMap<String, String> formData) {
		this.formData = formData;
	}

}
