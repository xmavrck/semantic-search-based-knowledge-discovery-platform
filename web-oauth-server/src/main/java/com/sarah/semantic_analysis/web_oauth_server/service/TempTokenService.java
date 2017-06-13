package com.sarah.semantic_analysis.web_oauth_server.service;

import java.util.concurrent.ExecutionException;

import com.sarah.semantic_analysis.web_oauth_server.dto.OAuthRequestTokenParams;
import com.sarah.semantic_analysis.web_oauth_server.entities.OAuthTemp;

/**
 * The interface TempTokenService.
 * 
 * @author chandan
 */
public interface TempTokenService {
	/**
	 * verifiyRequestToken
	 * 
	 * @param nonce
	 * @param timestamp
	 * @return boolean
	 */
	public boolean verifiyRequestToken(String nonce, String timestamp);

	/**
	 * findByConsumerKey
	 * 
	 * @param consumerKey
	 * @return OAuthTemp
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public OAuthTemp findByConsumerKey(String consumerKey) throws InterruptedException, ExecutionException;

	/**
	 * updateOAuthVerifier
	 * 
	 * @param oAuthToken
	 * @param userId
	 * @return OAuthTemp
	 * @throws Exception
	 */
	public OAuthTemp updateOAuthVerifier(String oAuthToken, String userId) throws Exception;

	/**
	 * deleteTemporaryToken
	 * 
	 * @param oAuthToken
	 * @param oAuthVerifier
	 * @return boolean
	 * @throws Exception
	 */
	public Integer deleteTempToken(String consumerKey, String userId) throws InterruptedException, ExecutionException;

	/**
	 * validateOAuthVerifier
	 * 
	 * @param oAuthToken
	 * @param oAuthVerifier
	 * @param consumerKey
	 * @return String
	 * @throws Exception
	 */
	public String validateOAuthVerifier(String oAuthToken, String oAuthVerifier, String consumerKey) throws Exception;

	/**
	 * deleteTempToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Integer
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public boolean deleteTemporaryToken(String oAuthToken, String oAuthVerifier) throws Exception;

	/**
	 * getCallBackUrl
	 * 
	 * @param oauth_token
	 * @return String
	 */
	public String getCallBackUrl(String oauth_token);

	/**
	 * findByNonceAndConsumerKey
	 * 
	 * @param nonce
	 * @param consumerKey
	 * @return OAuthTemp
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public OAuthTemp findByNonceAndConsumerKey(String nonce, String consumerKey)
			throws InterruptedException, ExecutionException;

	/**
	 * saveTempToken
	 * 
	 * @param params
	 * @return String[]
	 */
	public String[] saveTempToken(OAuthRequestTokenParams params);
}
