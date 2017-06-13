package org.sarah.web.client.oauthclient1.types;

/**
 * The class RequestToken.
 * 
 * @author chandan
 */
public class RequestToken {
	private String oauth_callback_confirmed, oauth_token, oauth_token_secret;

	public String getOauth_callback_confirmed() {
		return oauth_callback_confirmed;
	}

	public void setOauth_callback_confirmed(String oauth_callback_confirmed) {
		this.oauth_callback_confirmed = oauth_callback_confirmed;
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

}
