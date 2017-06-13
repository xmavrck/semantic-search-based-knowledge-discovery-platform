package com.sarah.semantic_analysis.web_oauth_server.entities;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * The class AccessToken.
 * 
 * @author chandan
 */
@Document
public class AccessToken implements Serializable {

	@org.springframework.data.annotation.Id
	private String id;
	@NotNull
	@Size(min = 1, max = 300)
	private String accessToken;
	@NotNull
	@Size(min = 1, max = 300)
	private String tokenSecret;
	@NotNull
	@Size(min = 1, max = 300)
	private String consumerKey;
	@NotNull
	@Size(min = 1, max = 300)
	private String nonce;
	@NotNull
	@Size(min = 1, max = 300)
	private String userId;

	public AccessToken() {
	}
	/**
	 * AccessToken
	 * 
	 * @param accessToken
	 * @param tokenSecret
	 * @param consumerKey
	 * @param userId
	 */
	public AccessToken(String accessToken, String tokenSecret, String consumerKey, String userId) {
		super();
		this.accessToken = accessToken;
		this.tokenSecret = tokenSecret;
		this.consumerKey = consumerKey;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
