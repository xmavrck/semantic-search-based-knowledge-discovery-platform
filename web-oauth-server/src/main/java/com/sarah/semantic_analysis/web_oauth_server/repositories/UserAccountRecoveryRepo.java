package com.sarah.semantic_analysis.web_oauth_server.repositories;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.sarah.semantic_analysis.web_oauth_server.entities.UserAccountRecovery;

/**
 * The class UserAccountRecoveryRepo.
 * 
 * @author chandan
 */
@Transactional
public interface UserAccountRecoveryRepo
		extends CrudRepository<UserAccountRecovery, Integer>, QueryDslPredicateExecutor<UserAccountRecovery> {

	/**
	 * findByUserIdAndLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return Future<UserAccountRecovery>
	 */
	@Async
	public Future<UserAccountRecovery> findByUserIdAndLinkKey(String emailId, String linkKey);

	/**
	 * deleteByUserId
	 * 
	 * @param userId
	 * @return Future<UserAccountRecovery>
	 */
	@Async
	public Future<UserAccountRecovery> deleteByUserId(String userId);

	/**
	 * findByUserId
	 * 
	 * @param userId
	 * @return Future<List<UserAccountRecovery>>
	 */
	@Async
	public Future<List<UserAccountRecovery>> findByUserId(String userId);

	/**
	 * findByLinkKey
	 * 
	 * @param linkKey
	 * @return Future<List<UserAccountRecovery>>
	 */
	@Async
	public Future<List<UserAccountRecovery>> findByLinkKey(String linkKey);

	/**
	 * findByUserIdAndResetKey
	 * 
	 * @param userId
	 * @param resetKey
	 * @return Future<UserAccountRecovery>
	 */
	@Async
	public Future<UserAccountRecovery> findByUserIdAndResetKey(String userId, String resetKey);

}
