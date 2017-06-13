package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;

/*
 * The class KafkaMessage
 * 
 * @author Chandan
 * */
public class KafkaMessage implements Serializable {

	private String hdfsUri;
	private String hdfsFilePath;
	private String university;
	private String url;
	private String urlScrapTaskId;
	private long timestamp;
	private int pageCount;
	
	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getUrlScrapTaskId() {
		return urlScrapTaskId;
	}

	public void setUrlScrapTaskId(String urlScrapTaskId) {
		this.urlScrapTaskId = urlScrapTaskId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getHdfsUri() {
		return hdfsUri;
	}

	public String getHdfsFilePath() {
		return hdfsFilePath;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setHdfsUri(String hdfsUri) {
		this.hdfsUri = hdfsUri;
	}

	public void setHdfsFilePath(String hdfsFilePath) {
		this.hdfsFilePath = hdfsFilePath;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "hdfsUri=" + hdfsUri + ",hdfsfilepath=" + hdfsFilePath + ",timestamp=" + timestamp;
	}
}
