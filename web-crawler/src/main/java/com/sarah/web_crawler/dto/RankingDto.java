package com.sarah.web_crawler.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class RankingDto implements Serializable {

	List<String> QueryWords;
	List<String> ExpandedQueryWords;

	Map<String, String> ExtractedDocuments;

	Map<String, String> URLscores;

	public RankingDto() {
	}

	public RankingDto(List<String> queryWords, List<String> expandedQueryWords,
			Map<String, String> extractedDocuments) {
		super();
		QueryWords = queryWords;
		ExpandedQueryWords = expandedQueryWords;
		ExtractedDocuments = extractedDocuments;
	}

	public Map<String, String> getURLscores() {
		return URLscores;
	}

	public void setURLscores(Map<String, String> uRLscores) {
		URLscores = uRLscores;
	}

	public List<String> getQueryWords() {
		return QueryWords;
	}

	public List<String> getExpandedQueryWords() {
		return ExpandedQueryWords;
	}

	public Map<String, String> getExtractedDocuments() {
		return ExtractedDocuments;
	}

	public void setQueryWords(List<String> queryWords) {
		QueryWords = queryWords;
	}

	public void setExpandedQueryWords(List<String> expandedQueryWords) {
		ExpandedQueryWords = expandedQueryWords;
	}

	public void setExtractedDocuments(Map<String, String> extractedDocuments) {
		ExtractedDocuments = extractedDocuments;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
