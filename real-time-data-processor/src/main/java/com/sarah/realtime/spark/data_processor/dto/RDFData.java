package com.sarah.realtime.spark.data_processor.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The class RDFData
 * 
 * @author Chandan
 */
public class RDFData implements Serializable {

	private String university;
	private String url;
	private String email;
	private String name;
	private String[] researchAreas;
	private String[] publications;
	private String[] keywords;

	public void setResearchAreas(String[] researchAreas) {
		this.researchAreas = researchAreas;
	}

	public RDFData() {
	}

	public RDFData(String university, String url, String email, String[] researchAreas) {
		super();
		this.university = university;
		this.url = url;
		this.email = email;
		this.researchAreas = researchAreas;
	}

	public RDFData(String university, String url, String email, String[] researchAreas, String[] publications,
			String[] keywords) {
		super();
		this.university = university;
		this.url = url;
		this.email = email;
		this.researchAreas = researchAreas;
		this.publications = publications;
		this.keywords = keywords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniversity() {
		return university;
	}

	public String getUrl() {
		return url;
	}

	public String getEmail() {
		return email;
	}

	public String[] getPublications() {
		return publications;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String[] getResearchAreas() {
		return researchAreas;
	}

	public void setPublications(String[] publications) {
		this.publications = publications;
	}

}
