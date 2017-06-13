package com.sarah.semantic_analysis.web_oauth_server.service;

import com.sarah.semantic_analysis.web_oauth_server.dto.AccessToken;

/**
 * The class AccessTokenService.
 * 
 * @author chandan
 */
public interface AccessTokenService {
	/**
	 * validateAccessTokens
	 * 
	 * @param accessToken
	 * @param consumerKey
	 * @param accessTokenSecret
	 * @return AccessToken
	 */
	public com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken validateAccessTokens(String accessToken,
			String consumerKey, String accessTokenSecret);

	/**
	 * validateAccessToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return AccessToken
	 */
	public AccessToken validateAccessToken(String consumerKey, String userId);

	/**
	 * expireAccessToken
	 * 
	 * @param accessToken
	 * @param accessTokenSecret
	 * @param consumerKey
	 * @return boolean
	 */
	public boolean expireAccessToken(String accessToken, String accessTokenSecret, String consumerKey);

	/**
	 * validateAccessTokenConsumerKeyAccessToken
	 * 
	 * @param consumerKey
	 * @param accessToken
	 * @return AccessToken
	 */
	public AccessToken validateAccessTokenConsumerKeyAccessToken(String consumerKey, String accessToken);

	/**
	 * saveAccessToken
	 * 
	 * @param userId
	 * @param consumerKey
	 * @return AccessToken
	 */
	public AccessToken saveAccessToken(String userId, String consumerKey);

}
