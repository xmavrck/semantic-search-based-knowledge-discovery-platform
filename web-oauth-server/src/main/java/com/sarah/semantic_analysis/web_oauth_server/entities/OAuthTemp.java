package com.sarah.semantic_analysis.web_oauth_server.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * The class OAuthTemp.
 * 
 * @author chandan
 */
@Document
public class OAuthTemp implements Serializable {

	@org.springframework.data.annotation.Id
	private String tempTokenId;
	private String oAuthToken;
	private String callBackUrl;
	private String oAuthVerified;

	private String userId;
	private String oAuthTokenSecret;

	private Date timestamp;
	private String nonce;
	private String consumerKey;

	public OAuthTemp() {
		// TODO Auto-generated constructor stub
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

	public String getTempTokenId() {
		return tempTokenId;
	}

	public void setTempTokenId(String tempTokenId) {
		this.tempTokenId = tempTokenId;
	}

	public String getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getoAuthVerified() {
		return oAuthVerified;
	}

	public void setoAuthVerified(String oAuthVerified) {
		this.oAuthVerified = oAuthVerified;
	}

	public String getoAuthTokenSecret() {
		return oAuthTokenSecret;
	}

	public void setoAuthTokenSecret(String oAuthTokenSecret) {
		this.oAuthTokenSecret = oAuthTokenSecret;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
}
