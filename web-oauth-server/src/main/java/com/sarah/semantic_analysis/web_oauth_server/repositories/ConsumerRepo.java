package com.sarah.semantic_analysis.web_oauth_server.repositories;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.sarah.semantic_analysis.web_oauth_server.entities.ClientApp;

/**
 * The class ConsumerRepo.
 * 
 * @author chandan
 */
@Transactional
public interface ConsumerRepo extends CrudRepository<ClientApp, String> {
	/**
	 * findByConsumerKey
	 * 
	 * @param consumerKey
	 * @return Future<ClientApp>
	 */
	@Async
	public Future<ClientApp> findByConsumerKey(String consumerKey);

	/**
	 * findByAppName
	 * 
	 * @param appName
	 * @return Future<ClientApp>
	 */
	@Async
	public Future<ClientApp> findByAppName(String appName);

	/**
	 * findByUserId
	 * 
	 * @param userId
	 * @return Future<List<ClientApp>>
	 */
	@Async
	public Future<List<ClientApp>> findByUserId(String userId);
}
