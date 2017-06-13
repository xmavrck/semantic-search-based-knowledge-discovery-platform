package com.sarah.semantic_analysis.web_oauth_server.service;

import com.sarah.semantic_analysis.web_oauth_server.entities.UserAccountRecovery;
/**
 * The interface UserAccountRecoveryService.
 * 
 * @author chandan
 */
public interface UserAccountRecoveryService extends GenericService<UserAccountRecovery> {
	/**
	 * findByEmailIdAndLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery findByEmailIdAndLinkKey(String emailId, String linkKey);
	/**
	 * findByEmailIdAndResetKey
	 * 
	 * @param emailId
	 * @param resetKey
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery findByEmailIdAndResetKey(String emailId, String resetKey);
	/**
	 * deleteByUsermasterUserId
	 * 
	 * @param userId
	 * @return Long
	 */
	public Long deleteByUsermasterUserId(String userId);
	/**
	 * findAndDeleteByUserId
	 * 
	 * @param userId
	 * @return Integer
	 */
	public Integer findAndDeleteByUserId(String userId);
	/**
	 * updateActivationCodeAndLinkKey
	 * 
	 * @param resetKey
	 * @param linkKey
	 * @return Integer
	 */
	public Integer updateActivationCodeAndLinkKey(String resetKey, String linkKey);

}
