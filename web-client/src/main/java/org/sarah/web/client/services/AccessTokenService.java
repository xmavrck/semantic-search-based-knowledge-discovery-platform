package org.sarah.web.client.services;

import java.util.List;

import org.sarah.web.client.entities.AccessTokens;

/**
 * The class AccessTokenService.
 * 
 * @author chandan
 */
public interface AccessTokenService extends GenericService<AccessTokens> {
	/**
	 * findByAccessToken
	 * 
	 * @param sessionId
	 * @return List<AccessTokens>
	 */
	public List<AccessTokens> findByAccessToken(String sessionId);

	/**
	 * deleteByUserId
	 * 
	 * @param userId
	 */
	public void deleteByUserId(String userId);

	/**
	 * deleteByAccessToken
	 * 
	 * @param sessionId
	 */
	public void deleteByAccessToken(String sessionId);
}
