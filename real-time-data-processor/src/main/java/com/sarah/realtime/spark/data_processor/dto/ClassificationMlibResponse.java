package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;

/**
 * The class ClassificationMlibResponse
 * 
 * @author Chandan
 */
public class ClassificationMlibResponse implements Serializable {

	private boolean isProperDocument;
	private String[] topResearchAreas;
	private String email;

	public boolean isProperDocument() {
		return isProperDocument;
	}

	public void setProperDocument(boolean isProperDocument) {
		this.isProperDocument = isProperDocument;
	}

	public String[] getTopResearchAreas() {
		return topResearchAreas;
	}

	public void setTopResearchAreas(String[] topResearchAreas) {
		this.topResearchAreas = topResearchAreas;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
