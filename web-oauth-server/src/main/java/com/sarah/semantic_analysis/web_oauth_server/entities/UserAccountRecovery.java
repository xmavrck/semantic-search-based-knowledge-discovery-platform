package com.sarah.semantic_analysis.web_oauth_server.entities;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * The class UserAccountRecovery.
 * 
 * @author chandan
 */
@Document
public class UserAccountRecovery implements Serializable {

	@Id
	private String recoveryId;
	@NotNull
	@Size(min = 1, max = 100)
	private String userId;
	@NotNull
	@Size(min = 1, max = 100)
	private String linkKey;
	@NotNull
	@Size(min = 1, max = 100)
	private String resetKey;
	@NotNull
	@Size(min = 1, max = 100)
	private Date expiryDate;

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRecoveryId() {
		return recoveryId;
	}

	public void setRecoveryId(String recoveryId) {
		this.recoveryId = recoveryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getResetKey() {
		return resetKey;
	}

	public String getLinkKey() {
		return linkKey;
	}

	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

}
