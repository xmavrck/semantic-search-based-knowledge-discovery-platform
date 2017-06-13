package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The class UrlHistory
 * 
 * @author chandan
 */
public class UrlHistory implements Serializable {
	private String id;

	private String url;

	private String taskId;

	private String urlTrackStatus;
	
	private String hdfsFilePath;

	private boolean isRetrieved;


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


	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
