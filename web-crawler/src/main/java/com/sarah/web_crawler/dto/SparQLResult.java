package com.sarah.web_crawler.dto;

public class SparQLResult {
	private Results results;

	private Head head;

	public Results getResults() {
		return results;
	}

	public void setResults(Results results) {
		this.results = results;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	@Override
	public String toString() {
		return "ClassPojo [results = " + results + ", head = " + head + "]";
	}
}