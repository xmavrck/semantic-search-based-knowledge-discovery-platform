package com.sarah.semantic_analysis.web_oauth_server.dto;

/**
 * The class MatchCodeDto.
 * 
 * @author chandan
 */
public class MatchCodeDto {

	String passCode;
	String emailId, key, sstt, newPassword,confirmPassword;

	public String getKey() {
		return key;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSstt() {
		return sstt;
	}

	public void setSstt(String sstt) {
		this.sstt = sstt;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPassCode() {
		return passCode;
	}

	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
