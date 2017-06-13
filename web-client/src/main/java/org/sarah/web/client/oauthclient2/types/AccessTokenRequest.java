package org.sarah.web.client.oauthclient2.types;

/**
 * The class AccessTokenRequest.
 * 
 * @author chandan
 */
public class AccessTokenRequest {
	private String grant_type, code, redirect_uri, client_id, client_secret;
	public AccessTokenRequest() {
		// TODO Auto-generated constructor stub
	}
	public AccessTokenRequest(String code, String redirect_uri, String client_id, String client_secret) {
		super();
		grant_type="authorization_code";
		this.code = code;
		this.redirect_uri = redirect_uri;
		this.client_id = client_id;
		this.client_secret = client_secret;
	}

	public AccessTokenRequest(String grant_type, String code, String redirect_uri, String client_id,
			String client_secret) {
		super();
		this.grant_type = grant_type;
		this.code = code;
		this.redirect_uri = redirect_uri;
		this.client_id = client_id;
		this.client_secret = client_secret;
	}

	public String getGrant_type() {
		return grant_type;
	}
	
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	
}
