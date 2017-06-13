package com.sarah.semantic_analysis.web_oauth_server.repositories;

import java.util.concurrent.Future;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken;

/**
 * The class AccessTokenRepo.
 * 
 * @author chandan
 */
@Transactional
public interface AccessTokenRepo extends CrudRepository<AccessToken, Long> {
	/**
	 * findByAccessTokenAndConsumerKey
	 * 
	 * @param accessToken
	 * @param consumerKey
	 * @return Future<AccessToken>
	 */
	@Async
	public Future<AccessToken> findByAccessTokenAndConsumerKey(String accessToken, String consumerKey);
	
	/**
	 * findByUserId
	 * 
	 * @param accessToken
	 * @return Future<AccessToken>
	 */
	@Async
	public Future<AccessToken> findByUserId(String accessToken);
	
	/**
	 * findByAccessTokenAndTokenSecretAndConsumerKey
	 * 
	 * @param accessToken
	 * @param tokenSecret
	 * @param consumerKey
	 * @return Future<AccessToken>
	 */
	@Async
	public Future<AccessToken> findByAccessTokenAndTokenSecretAndConsumerKey(String accessToken, String tokenSecret,
			String consumerKey);

}
