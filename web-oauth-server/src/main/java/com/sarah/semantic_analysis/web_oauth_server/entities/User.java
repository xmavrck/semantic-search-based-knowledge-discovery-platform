package com.sarah.semantic_analysis.web_oauth_server.entities;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * The class User.
 * 
 * @author chandan
 */
@Document
public class User implements Serializable {

	@org.springframework.data.annotation.Id
	private String id;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String firstName;
	
	@NotNull
	@Size(min = 1, max = 100)
	private String lastName;

	@NotNull
	@Size(min = 6, max = 100)
	private String password;

	@NotNull
	@Size(min = 5, max = 200)
	private String emailId;

	@NotNull
	@Size(min = 10, max = 20)
	private String mobileNumber;

	@NotNull
	private String linkKey;

	@NotNull
	private Date dateAdded;

	@NotNull
	private Date expiryDate;
	
	@NotNull
	private String role;

	@NotNull
	private Boolean isEnabled;
	
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User() {
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getLinkKey() {
		return linkKey;
	}

	public void setLinkKey(String linkKey) {
		this.linkKey = linkKey;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
