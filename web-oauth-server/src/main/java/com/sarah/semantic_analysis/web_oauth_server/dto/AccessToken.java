package com.sarah.semantic_analysis.web_oauth_server.dto;
/**
 * The class AccessToken.
 * 
 * @author chandan
 */
public class AccessToken {
	String oauth_token;
	String user_id;
	String oauth_token_secret;
	String status;
	Integer code;

	public AccessToken() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * employees
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return String
	 * @throws Exception
	 */
	public AccessToken(String oauth_token, String user_id, String oauth_token_secret, String status, Integer code) {
		super();
		this.oauth_token = oauth_token;
		this.user_id = user_id;
		this.oauth_token_secret = oauth_token_secret;
		this.status = status;
		this.code = code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOauth_token() {
		return oauth_token;
	}

	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}

	public String getOauth_token_secret() {
		return oauth_token_secret;
	}

	public void setOauth_token_secret(String oauth_token_secret) {
		this.oauth_token_secret = oauth_token_secret;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
