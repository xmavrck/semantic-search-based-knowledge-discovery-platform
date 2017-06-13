package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;

/**
 * The class BuildSparQLRequest
 * 
 * @author Chandan
 */
public class BuildSparQLRequest implements Serializable {

	private String text;

	public BuildSparQLRequest(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
