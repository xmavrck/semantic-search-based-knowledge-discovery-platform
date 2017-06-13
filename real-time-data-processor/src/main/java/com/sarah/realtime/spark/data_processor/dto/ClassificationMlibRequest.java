package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;

/**
 * The class ClassificationMlibRequest
 * 
 * @author Chandan
 */
public class ClassificationMlibRequest implements Serializable {

	private String text;

	public ClassificationMlibRequest(String text) {
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
