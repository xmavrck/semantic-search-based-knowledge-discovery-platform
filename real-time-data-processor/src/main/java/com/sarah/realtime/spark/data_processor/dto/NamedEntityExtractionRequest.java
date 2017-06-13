package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;
/*
 * The class NamedEntityExtractionRequest
 * 
 * @author Chandan
 * */
public class NamedEntityExtractionRequest implements Serializable {

	private String email;
	private String text;
	
	public NamedEntityExtractionRequest(String email, String text) {
		super();
		this.email = email;
		this.text = text;
	}

	public String getEmail() {
		return email;
	}

	public String getText() {
		return text;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setText(String text) {
		this.text = text;
	}

}
