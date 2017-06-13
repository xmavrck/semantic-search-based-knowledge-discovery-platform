package com.sarah.semantic_analysis.web_oauth_server.repositories;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.sarah.semantic_analysis.web_oauth_server.entities.OAuthTemp;

/**
 * The interface TempTokenRepo.
 * 
 * @author chandan
 */
@Transactional
public interface TempTokenRepo extends CrudRepository<OAuthTemp, Long> {
	/**
	 * findByOAuthTokenAndOAuthVerified
	 * 
	 * @param oAuthToken
	 * @param oAuthVerified
	 * @return Future<OAuthTemp>
	 */
	@Async
	public Future<OAuthTemp> findByOAuthTokenAndOAuthVerified(String oAuthToken, String oAuthVerified);

	/**
	 * findByOAuthTokenAndOAuthVerifiedAndConsumerKey
	 * 
	 * @param oAuthToken
	 * @param oAuthVerified
	 * @param consumerKey
	 * @return Future<List<OAuthTemp>>
	 */
	@Async
	public Future<List<OAuthTemp>> findByOAuthTokenAndOAuthVerifiedAndConsumerKey(String oAuthToken,
			String oAuthVerified, String consumerKey);

	/**
	 * findByOAuthToken
	 * 
	 * @param oAuthToken
	 * @return Future<OAuthTemp>
	 */
	@Async
	public Future<OAuthTemp> findByOAuthToken(String oAuthToken);

	/**
	 * findByNonceAndTimestamp
	 * 
	 * @param nonce
	 * @param timestamp
	 * @return Future<OAuthTemp>
	 */
	@Async
	public Future<OAuthTemp> findByNonceAndTimestamp(String nonce, Date timestamp);

	/**
	 * findByNonceAndConsumerKey
	 * 
	 * @param nonce
	 * @param consumerKey
	 * @return Future<OAuthTemp>
	 */
	@Async
	public Future<OAuthTemp> findByNonceAndConsumerKey(String nonce, String consumerKey);

	/**
	 * findByConsumerKey
	 * 
	 * @param consumerKey
	 * @return Future<OAuthTemp>
	 */
	@Async
	public Future<OAuthTemp> findByConsumerKey(String consumerKey);

	/**
	 * deleteByOAuthTokenAndOAuthVerified
	 * 
	 * @param oAuthToken
	 * @param oAuthVerified
	 * @return Future<Long>
	 */
	@Async
	public Future<Long> deleteByOAuthTokenAndOAuthVerified(String oAuthToken, String oAuthVerified);
}
