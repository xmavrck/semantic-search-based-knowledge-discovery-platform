package com.sarah.semantic_analysis.web_oauth_server.dao;

import java.util.concurrent.Future;

import com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken;

/**
 * The class AccessTokenDao.
 * 
 * @author chandan
 */
public interface AccessTokenDao {
	/**
	 * validateAccessToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Future<AccessToken>
	 */
	Future<AccessToken> validateAccessToken(String consumerKey, String userId);
	/**
	 * validateAccessTokenConsumerKey
	 * 
	 * @param consumerKey
	 * @param accessToken
	 * @return Future<AccessToken>
	 */
	Future<AccessToken> validateAccessTokenConsumerKey(String consumerKey, String accessToken);

}
