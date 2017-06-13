package org.sarah.web.client.dao;

import java.util.List;

import org.sarah.web.client.entities.AccessTokens;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * The interface AccessTokenDao.
 * 
 * @author chandan
 */
public interface AccessTokenDao extends GenericDao<AccessTokens> {
	/**
	 * findByAccessToken
	 * 
	 * @param token
	 * @return List<AccessTokens>
	 */
	List<AccessTokens> findByAccessToken(String token);

	/**
	 * findByUserId
	 * 
	 * @param userId
	 * @return List<AccessTokens>
	 */
	List<AccessTokens> findByUserId(String userId);

	/**
	 * deleteByAccessToken
	 * 
	 * @param token
	 */
	void deleteByAccessToken(String token);

	/**
	 * deleteByUserId
	 * 
	 * @param userId
	 */
	void deleteByUserId(String userId);

	/**
	 * getMongoTemplate
	 * 
	 * @return MongoTemplate
	 */
	public MongoTemplate getMongoTemplate();
}
