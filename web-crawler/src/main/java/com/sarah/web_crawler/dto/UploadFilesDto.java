package com.sarah.web_crawler.dto;

import java.io.Serializable;

/**
 * The class UploadFilesDto
 * @author chandan
 */
public class UploadFilesDto implements Serializable {
	private String taskName;

	public UploadFilesDto() {
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
