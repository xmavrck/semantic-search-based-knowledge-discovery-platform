package com.sarah.web_crawler.dto;

public class AggregateDto {

	String university;
	String researchArea;
	Integer publicationCount;
	Integer peopleCount;
	

	public AggregateDto(String university, String researchArea, Integer publicationCount, Integer peopleCount) {
		super();
		this.university = university;
		this.researchArea = researchArea;
		this.publicationCount = publicationCount;
		this.peopleCount = peopleCount;
	}

	public String getUniversity() {
		return university;
	}

	public String getResearchArea() {
		return researchArea;
	}

	public Integer getPublicationCount() {
		return publicationCount;
	}

	public Integer getPeopleCount() {
		return peopleCount;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public void setResearchArea(String researchArea) {
		this.researchArea = researchArea;
	}

	public void setPublicationCount(Integer publicationCount) {
		this.publicationCount = publicationCount;
	}

	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}

}
