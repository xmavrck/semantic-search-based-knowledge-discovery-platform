package com.sarah.web_crawler.dto;

import java.util.Date;

import com.google.gson.Gson;

/**
 * The class UrlScrapJob
 * 
 * @author chandan
 */
public class UrlScrapJobDto {

	private String id;

	private String rootUrl;

	private String university;

	private String taskStatus;

	private Date dateAdded;

	private Date dateCompleted;

	private String taskType;

	private Long noOfUrlsProcessed;

	private Long noOfUrlsRetrieved;

	public Long getNoOfUrlsProcessed() {
		return noOfUrlsProcessed;
	}

	public Long getNoOfUrlsRetrieved() {
		return noOfUrlsRetrieved;
	}

	public void setNoOfUrlsProcessed(Long noOfUrlsProcessed) {
		this.noOfUrlsProcessed = noOfUrlsProcessed;
	}

	public void setNoOfUrlsRetrieved(Long noOfUrlsRetrieved) {
		this.noOfUrlsRetrieved = noOfUrlsRetrieved;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public UrlScrapJobDto(String id, String rootUrl, String university, String taskStatus, Date dateAdded,
			Date dateCompleted, String taskType) {
		super();
		this.id = id;
		this.rootUrl = rootUrl;
		this.university = university;
		this.taskStatus = taskStatus;
		this.dateAdded = dateAdded;
		this.dateCompleted = dateCompleted;
		this.taskType = taskType;
	}

	
	public UrlScrapJobDto(String id, String rootUrl, String university, String taskStatus, Date dateAdded,
			Date dateCompleted, String taskType, Long noOfUrlsProcessed, Long noOfUrlsRetrieved) {
		super();
		this.id = id;
		this.rootUrl = rootUrl;
		this.university = university;
		this.taskStatus = taskStatus;
		this.dateAdded = dateAdded;
		this.dateCompleted = dateCompleted;
		this.taskType = taskType;
		this.noOfUrlsProcessed = noOfUrlsProcessed;
		this.noOfUrlsRetrieved = noOfUrlsRetrieved;
	}

	public UrlScrapJobDto() {
	}

	public UrlScrapJobDto(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public UrlScrapJobDto(String rootUrl, Date dateAdded) {
		super();
		this.rootUrl = rootUrl;
		this.dateAdded = dateAdded;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getId() {
		return id;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
