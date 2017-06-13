package org.sarah.web.client.services;

import org.sarah.web.client.entities.AccessTokens;
import org.sarah.web.client.entities.User;

/**
 * The interface UserService.
 * 
 * @author chandan
 */
public interface UserService extends GenericService<User> {
	/**
	 * createLocalAccount
	 * 
	 * @param accessToken
	 * @return User
	 */
	public User createLocalAccount(AccessTokens accessToken);

}
