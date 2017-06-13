package com.sarah.semantic_analysis.web_oauth_server.entities;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * The class AccessTokenVerifiers.
 * 
 * @author chandan
 */
@Document
public class AccessTokenVerifiers implements Serializable {

	@org.springframework.data.annotation.Id
	private String id;
	@NotNull
	@Size(min = 1, max = 300)
	private String accessToken;
	@NotNull
	@Size(min = 1, max = 300)
	private String nonce;

	public AccessTokenVerifiers() {
	}
	/**
	 * AccessTokenVerifiers
	 * 
	 * @param accessToken
	 * @param nonce
	 */
	public AccessTokenVerifiers(String accessToken, String nonce) {
		super();
		this.accessToken = accessToken;
		this.nonce = nonce;
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

}
