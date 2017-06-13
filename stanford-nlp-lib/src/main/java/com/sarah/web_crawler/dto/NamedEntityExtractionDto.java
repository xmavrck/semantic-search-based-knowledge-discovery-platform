package com.sarah.web_crawler.dto;

import java.io.Serializable;

public class NamedEntityExtractionDto implements Serializable {

	private String email;
	private String text;

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
