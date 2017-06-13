package org.sarah.web.client.oauthclient2.types;

/**
 * The class AccessTokenResponse.
 * 
 * @author chandan
 */
public class AccessTokenResponse {
	private String access_token,error,error_description;
	// no of seconds
	private Long expires_in,token_type;
//	{"error":{"message":"client_secret should not be passed to \/oauth\/accessToken","type":"OAuthException","code":1,"fbtrace_id":"AHQf8V+p1Wo"}}
	public String getError() {
		return error;
	}	
	
	public Long getToken_type() {
		return token_type;
	}
	
	public void setToken_type(Long token_type) {
		this.token_type = token_type;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}

	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}
}
