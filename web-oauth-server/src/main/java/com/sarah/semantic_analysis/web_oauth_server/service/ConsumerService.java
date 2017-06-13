package com.sarah.semantic_analysis.web_oauth_server.service;

import java.util.List;

import com.sarah.semantic_analysis.web_oauth_server.dto.OauthRegParam;
import com.sarah.semantic_analysis.web_oauth_server.entities.ClientApp;

/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
public interface ConsumerService extends GenericService<ClientApp> {
	/**
	 * validateConsumerKey
	 * 
	 * @param consumerKey
	 * @return String
	 */
	public String validateConsumerKey(String consumerKey);

	/**
	 * saveClientApp
	 * 
	 * @param clientApp
	 * @return Object
	 */
	public Object saveClientApp(OauthRegParam clientApp);

	/**
	 * findByConsumerKey
	 * 
	 * @param consumerKey
	 * @return ClientApp
	 */
	public ClientApp findByConsumerKey(String consumerKey);

	/**
	 * findByAppName
	 * 
	 * @param appName
	 * @return ClientApp
	 */
	public ClientApp findByAppName(String appName);

	/**
	 * findByUserId
	 * 
	 * @param userId
	 * @return List<ClientApp>
	 */
	public List<ClientApp> findByUserId(String userId);
}
