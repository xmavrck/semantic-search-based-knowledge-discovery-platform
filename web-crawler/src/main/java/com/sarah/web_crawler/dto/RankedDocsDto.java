package com.sarah.web_crawler.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

public class RankedDocsDto implements Serializable {
	Map<String, String> data;
	double score;
	String url;
	Set<String> otherRelatedAreas;
	
	public Set<String> getOtherRelatedAreas() {
		return otherRelatedAreas;
	}

	public void setOtherRelatedAreas(Set<String> otherRelatedAreas) {
		this.otherRelatedAreas = otherRelatedAreas;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getData() {
		return data;
	}

	public double getScore() {
		return score;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public void setScore(double score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
