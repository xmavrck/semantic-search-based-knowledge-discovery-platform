package com.sarah.web_crawler.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The class BuildSparQLResponse
 * 
 * @author Chandan
 */
public class BuildSparQLResponse implements Serializable {

	private List<String> words;

	public List<String> getWords() {
		return words;
	}
	
	public void setWords(List<String> words) {
		this.words = words;
	}

}
