package com.sarah.web_crawler.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The class WebScrappyDataDto
 * @author chandan
 */
public class WebScrappyDataDto implements Serializable {
	private String id;

	private String url;

	private String taskId;

	private String content;

	private Date dateAdded;

	public WebScrappyDataDto(String id, String url, String taskId, String content, Date dateAdded) {
		super();
		this.id = id;
		this.url = url;
		this.taskId = taskId;
		this.content = content;
		this.dateAdded = dateAdded;
	}

	public WebScrappyDataDto() {
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
