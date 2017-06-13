package com.sarah.web_crawler.dto;

import java.io.Serializable;

public class NamedEntityExtractionResponse implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NamedEntityExtractionResponse(String name) {
		super();
		this.name = name;
	}

}
