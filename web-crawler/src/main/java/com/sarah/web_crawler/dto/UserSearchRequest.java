package com.sarah.web_crawler.dto;

import java.io.Serializable;
import java.util.List;

public class UserSearchRequest implements Serializable {

	private List<String> researchAreas;
	private List<String> universities;
	private String keywordBased;
	
	private String projectDescription;
	private String text;
	private List<String> words;
	
	
	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getKeywordBased() {
		return keywordBased;
	}

	public void setKeywordBased(String keywordBased) {
		this.keywordBased = keywordBased;
	}

	public List<String> getWords() {
		return words;
	}

	public List<String> getResearchAreas() {
		return researchAreas;
	}

	public List<String> getUniversities() {
		return universities;
	}

	public void setResearchAreas(List<String> researchAreas) {
		this.researchAreas = researchAreas;
	}

	public void setUniversities(List<String> universities) {
		this.universities = universities;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
