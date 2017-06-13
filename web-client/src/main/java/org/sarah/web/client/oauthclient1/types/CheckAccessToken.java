package org.sarah.web.client.oauthclient1.types;

/**
 * The class CheckAccessToken.
 * 
 * @author chandan
 */
public class CheckAccessToken {
	String userId, status, code;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
