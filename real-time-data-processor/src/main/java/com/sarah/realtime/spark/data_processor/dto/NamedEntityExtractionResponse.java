package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;

/*
 * The class NamedEntityExtractionResponse
 * 
 * @author Chandan
 * */
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
