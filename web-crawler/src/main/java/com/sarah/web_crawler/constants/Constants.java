package com.sarah.web_crawler.constants;
/**
 * The interface Constants.
 * 
 * @author chandan
 */
public interface Constants {
	/**
	 * BASE_PACKAGE_NAME
	 */
	String BASE_PACKAGE_NAME = "com.sarah.web.crawler.web_crawler.entities";

	/**
	 * The interface JobStatus
	 */
	public interface JobStatus {
		String ACCEPTED = "accepted";
		String RUNNING = "running";
		String FAILED = "failed";
		String KILLED = "killed";
		String COMPLETED = "completed";
	}
	
	/**
	 * The interface TaskType
	 */
	public interface TaskType {
		String WEBSITE = "website";
		String FILE = "file";
	}
	/**
	 * The interface Http
	 */
	public interface Http{
		String USER_AGENT="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";
		
	}
}