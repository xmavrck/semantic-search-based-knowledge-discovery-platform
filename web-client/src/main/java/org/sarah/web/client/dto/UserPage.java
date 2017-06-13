package org.sarah.web.client.dto;

import org.sarah.web.client.entities.User;
/**
 * The class UserPage.
 * @author chandan
 */
public class UserPage {
	private User[] content;

	private int numberOfElements;

	private String sort;

	private boolean last;

	private int totalElements;

	private int number;

	private boolean first;

	private int totalPages;

	private int size;

	public UserPage() {
	}

	public User[] getContent() {
		return content;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public String getSort() {
		return sort;
	}

	public boolean isLast() {
		return last;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public int getNumber() {
		return number;
	}

	public boolean isFirst() {
		return first;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getSize() {
		return size;
	}

	public void setContent(User[] content) {
		this.content = content;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "ClassPojo [content = " + content + ", numberOfElements = " + numberOfElements + ", sort = " + sort
				+ ", last = " + last + ", totalElements = " + totalElements + ", number = " + number + ", first = "
				+ first + ", totalPages = " + totalPages + ", size = " + size + "]";
	}
}
