package com.sarah.web_crawler.entities;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.Gson;

/**
 * The class UrlScrapJob
 * @author chandan
 */
@Document
public class UrlScrapJob {

	@Id
	private String id;

	private String rootUrl;
	
	private String university;

	private String taskStatus;
	
	private String hdfsDirectoryPath;

	private Date dateAdded;
	
	private Date dateCompleted;

	private String taskType;
	
	public String getHdfsDirectoryPath() {
		return hdfsDirectoryPath;
	}

	public void setHdfsDirectoryPath(String hdfsDirectoryPath) {
		this.hdfsDirectoryPath = hdfsDirectoryPath;
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

	public UrlScrapJob() {
	}

	public UrlScrapJob(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public UrlScrapJob(String rootUrl, Date dateAdded) {
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
