package com.sarah.semantic_analysis.web_oauth_server.dao;

import java.util.concurrent.Future;
/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
public interface UserRecoveryDao {
	/**
	 * findAndDelete
	 * 
	 * @param userId
	 * @return Future<Integer>
	 */
	public Future<Integer> findAndDelete(String userId);
	/**
	 * deleteTempToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Future<Integer>
	 */
	public Future<Integer> deleteTempToken(String consumerKey, String userId);
}
