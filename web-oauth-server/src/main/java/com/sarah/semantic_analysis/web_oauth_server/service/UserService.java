package com.sarah.semantic_analysis.web_oauth_server.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sarah.semantic_analysis.web_oauth_server.dto.SearchUserDto;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;

/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
public interface UserService extends GenericService<User> {
	/**
	 * findByEmailAndPassword
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public User findByEmailAndPassword(String emailId, String password) throws InterruptedException, ExecutionException;

	/**
	 * findByEmail
	 * 
	 * @param emailId
	 * @return User
	 */
	public User findByEmail(String emailId);

	/**
	 * findById
	 * 
	 * @param id
	 * @return User
	 */
	public User findById(String id);

	/**
	 * findByEmailIdAndLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return User
	 */
	public User findByEmailIdAndLinkKey(String emailId, String linkKey);

	/**
	 * findByIsEnabled
	 * 
	 * @param isEnabled
	 * @return List<User>
	 */
	public List<User> findByIsEnabled(Boolean isEnabled);

	/**
	 * findByUserIdNotIn
	 * 
	 * @param userId
	 * @return Page<User>
	 */
	public Page<User> findByUserIdNotIn(String userId);

	/**
	 * findByMobileNumber
	 * 
	 * @param mobileNumber
	 * @return User
	 */
	public User findByMobileNumber(String mobileNumber);

	/**
	 * findByEmailIdIgnoreCaseAndPassword
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 */
	public User findByEmailIdIgnoreCaseAndPassword(String emailId, String password);

	/**
	 * findByEmailIdAndUserAccountRecoveryLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return User
	 */
	public User findByEmailIdAndUserAccountRecoveryLinkKey(String emailId, String linkKey);

	/**
	 * updatePassCodeAndPassCodeAddDate
	 * 
	 * @param passCode
	 * @param passCodeAddDate
	 * @param emailId
	 * @return Integer
	 */
	public Integer updatePassCodeAndPassCodeAddDate(String passCode, Date passCodeAddDate, String emailId);

	/**
	 * search
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return Page<User>
	 * @throws Exception
	 */
	public Page<User> search(SearchUserDto searchUserDto, Pageable pageable) throws Exception;

}
