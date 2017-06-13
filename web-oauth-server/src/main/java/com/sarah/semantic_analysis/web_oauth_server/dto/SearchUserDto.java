package com.sarah.semantic_analysis.web_oauth_server.dto;

import java.util.List;
/**
 * The class SearchUserDto.
 * 
 * @author chandan
 */
public class SearchUserDto {

	private Integer id;
	// firstname filters
	private String firstName;
	private String firstName__i;
	private String[] firstName__in;
	private String firstName__startswith;
	private String firstName__endswith;
	private String firstName__contains;
	private String firstName__icontains;

	// last name filters
	private String lastName;
	private String lastName__i;
	private String[] lastName__in;
	private String lastName__startswith;
	private String lastName__endswith;
	private String lastName__contains;
	private String lastName__icontains;

	// email filters
	private String emailId;
	private String emailId__i;
	private String[] emailId__in;
	private String emailId__startswith;
	private String emailId__endswith;
	private String emailId__contains;
	private String emailId__icontains;

	// password filters
	private String password;

	// phone number filters
	private String phoneNumber;
	private String phoneNumber__i;
	private String[] phoneNumber__in;
	private String phoneNumber__startswith;
	private String phoneNumber__endswith;
	private String phoneNumber__contains;
	private String phoneNumber__icontains;

	// isEnabled filters
	private Boolean isEnabled;

	// isEnabled filters
	private String role;
	
	// creationDate filters
	private String creationDate;
	private String creationDate__gt;
	private String creationDate__gte;
	private String creationDate__lt;
	private String creationDate__lte;
	private String[] creationDate__between;

	// current latlog filters
	private List<Double> curLatLog;
	// maximum distance for searching
	private Double maxDistanceInKm;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName__i() {
		return firstName__i;
	}

	public void setFirstName__i(String firstName__i) {
		this.firstName__i = firstName__i;
	}

	public String[] getFirstName__in() {
		return firstName__in;
	}

	public void setFirstName__in(String[] firstName__in) {
		this.firstName__in = firstName__in;
	}

	public String getFirstName__startswith() {
		return firstName__startswith;
	}

	public void setFirstName__startswith(String firstName__startswith) {
		this.firstName__startswith = firstName__startswith;
	}

	public String getFirstName__endswith() {
		return firstName__endswith;
	}

	public void setFirstName__endswith(String firstName__endswith) {
		this.firstName__endswith = firstName__endswith;
	}

	public String getFirstName__contains() {
		return firstName__contains;
	}

	public void setFirstName__contains(String firstName__contains) {
		this.firstName__contains = firstName__contains;
	}

	public String getFirstName__icontains() {
		return firstName__icontains;
	}

	public void setFirstName__icontains(String firstName__icontains) {
		this.firstName__icontains = firstName__icontains;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName__i() {
		return lastName__i;
	}

	public void setLastName__i(String lastName__i) {
		this.lastName__i = lastName__i;
	}

	public String[] getLastName__in() {
		return lastName__in;
	}

	public void setLastName__in(String[] lastName__in) {
		this.lastName__in = lastName__in;
	}

	public String getLastName__startswith() {
		return lastName__startswith;
	}

	public void setLastName__startswith(String lastName__startswith) {
		this.lastName__startswith = lastName__startswith;
	}

	public String getLastName__endswith() {
		return lastName__endswith;
	}

	public void setLastName__endswith(String lastName__endswith) {
		this.lastName__endswith = lastName__endswith;
	}

	public String getLastName__contains() {
		return lastName__contains;
	}

	public void setLastName__contains(String lastName__contains) {
		this.lastName__contains = lastName__contains;
	}

	public String getLastName__icontains() {
		return lastName__icontains;
	}

	public void setLastName__icontains(String lastName__icontains) {
		this.lastName__icontains = lastName__icontains;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailId__i() {
		return emailId__i;
	}

	public void setEmailId__i(String emailId__i) {
		this.emailId__i = emailId__i;
	}

	public String[] getEmailId__in() {
		return emailId__in;
	}

	public void setEmailId__in(String[] emailId__in) {
		this.emailId__in = emailId__in;
	}

	public String getEmailId__startswith() {
		return emailId__startswith;
	}

	public void setEmailId__startswith(String emailId__startswith) {
		this.emailId__startswith = emailId__startswith;
	}

	public String getEmailId__endswith() {
		return emailId__endswith;
	}

	public void setEmailId__endswith(String emailId__endswith) {
		this.emailId__endswith = emailId__endswith;
	}

	public String getEmailId__contains() {
		return emailId__contains;
	}

	public void setEmailId__contains(String emailId__contains) {
		this.emailId__contains = emailId__contains;
	}

	public String getEmailId__icontains() {
		return emailId__icontains;
	}

	public void setEmailId__icontains(String emailId__icontains) {
		this.emailId__icontains = emailId__icontains;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber__i() {
		return phoneNumber__i;
	}

	public void setPhoneNumber__i(String phoneNumber__i) {
		this.phoneNumber__i = phoneNumber__i;
	}

	public String[] getPhoneNumber__in() {
		return phoneNumber__in;
	}

	public void setPhoneNumber__in(String[] phoneNumber__in) {
		this.phoneNumber__in = phoneNumber__in;
	}

	public String getPhoneNumber__startswith() {
		return phoneNumber__startswith;
	}

	public void setPhoneNumber__startswith(String phoneNumber__startswith) {
		this.phoneNumber__startswith = phoneNumber__startswith;
	}

	public String getPhoneNumber__endswith() {
		return phoneNumber__endswith;
	}

	public void setPhoneNumber__endswith(String phoneNumber__endswith) {
		this.phoneNumber__endswith = phoneNumber__endswith;
	}

	public String getPhoneNumber__contains() {
		return phoneNumber__contains;
	}

	public void setPhoneNumber__contains(String phoneNumber__contains) {
		this.phoneNumber__contains = phoneNumber__contains;
	}

	public String getPhoneNumber__icontains() {
		return phoneNumber__icontains;
	}

	public void setPhoneNumber__icontains(String phoneNumber__icontains) {
		this.phoneNumber__icontains = phoneNumber__icontains;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationDate__gt() {
		return creationDate__gt;
	}

	public void setCreationDate__gt(String creationDate__gt) {
		this.creationDate__gt = creationDate__gt;
	}

	public String getCreationDate__gte() {
		return creationDate__gte;
	}

	public void setCreationDate__gte(String creationDate__gte) {
		this.creationDate__gte = creationDate__gte;
	}

	public String getCreationDate__lt() {
		return creationDate__lt;
	}

	public void setCreationDate__lt(String creationDate__lt) {
		this.creationDate__lt = creationDate__lt;
	}

	public String getCreationDate__lte() {
		return creationDate__lte;
	}

	public void setCreationDate__lte(String creationDate__lte) {
		this.creationDate__lte = creationDate__lte;
	}

	public String[] getCreationDate__between() {
		return creationDate__between;
	}

	public void setCreationDate__between(String[] creationDate__between) {
		this.creationDate__between = creationDate__between;
	}

	public List<Double> getCurLatLog() {
		return curLatLog;
	}

	public void setCurLatLog(List<Double> curLatLog) {
		this.curLatLog = curLatLog;
	}

	public Double getMaxDistanceInKm() {
		return maxDistanceInKm;
	}

	public void setMaxDistanceInKm(Double maxDistanceInKm) {
		this.maxDistanceInKm = maxDistanceInKm;
	}

}
