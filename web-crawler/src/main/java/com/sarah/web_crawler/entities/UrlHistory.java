package com.sarah.web_crawler.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The class UrlHistory
 * 
 * @author chandan
 */
@Document
public class UrlHistory implements Serializable {
	@org.springframework.data.annotation.Id
	private String id;

	private String url;

	private String taskId;

	private String urlTrackStatus;
	
	private String hdfsFilePath;

	private boolean isRetrieved;

	private Date dateAdded;

	public UrlHistory() {
	}

	
	public UrlHistory(String url) {
		super();
		this.url = url;
	}

	public UrlHistory(String id, String url) {
		super();
		this.id = id;
		this.url = url;
	}

	public boolean isRetrieved() {
		return isRetrieved;
	}
	
	public String getHdfsFilePath() {
		return hdfsFilePath;
	}


	public void setHdfsFilePath(String hdfsFilePath) {
		this.hdfsFilePath = hdfsFilePath;
	}


	public void setRetrieved(boolean isRetrieved) {
		this.isRetrieved = isRetrieved;
	}


	public String getUrlTrackStatus() {
		return urlTrackStatus;
	}

	public void setUrlTrackStatus(String urlTrackStatus) {
		this.urlTrackStatus = urlTrackStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public UrlHistory(String url, String taskId, Date dateAdded) {
		super();
		this.url = url;
		this.taskId = taskId;
		this.dateAdded = dateAdded;
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
