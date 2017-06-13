package com.sarah.semantic_analysis.web_oauth_server.dao;

import java.util.concurrent.Future;
/**
 * The class TempTokenDao.
 * 
 * @author chandan
 */
public interface TempTokenDao {
	/**
	 * deleteTempToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Future<Integer>
	 */
	Future<Integer> deleteTempToken(String consumerKey, String userId);
}
