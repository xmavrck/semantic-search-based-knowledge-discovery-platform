package com.sarah.semantic_analysis.web_oauth_server.dto;
/**
 * The class User.
 * 
 * @author chandan
 */
public class User {
	
    String emailId;
    String password;
    String oauth_token;

    public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getOauth_token() {
        return oauth_token;
    }

    public void setOauth_token(String oauth_token) {
        this.oauth_token = oauth_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
