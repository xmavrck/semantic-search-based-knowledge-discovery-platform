package com.sarah.semantic_analysis.web_oauth_server.types;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants.DBOperations;

/**
 * The class SearchCriteria.
 * 
 * @author chandan
 */
public class SearchCriteria {
	/**
	 * key
	 */
	private String key;
	/**
	 * operation
	 */
	private DBOperations operation;
	/**
	 * value
	 */
	private Object value;

	public SearchCriteria() {
		// TODO Auto-generated constructor stub
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DBOperations getOperation() {
		return operation;
	}

	public void setOperation(DBOperations operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SearchCriteria(String key, DBOperations operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public void setValues(String key, DBOperations operation, Object value) {
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

}
