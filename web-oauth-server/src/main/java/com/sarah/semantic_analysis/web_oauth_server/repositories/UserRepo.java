package com.sarah.semantic_analysis.web_oauth_server.repositories;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.annotation.Async;

import com.sarah.semantic_analysis.web_oauth_server.entities.User;

/**
 * The class UserRepo.
 * 
 * @author chandan
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepo extends MongoRepository<User, String>, QueryDslPredicateExecutor<User> {
	/**
	 * findByEmailIdIgnoreCase
	 * 
	 * @param emailId
	 * @return Future<User>
	 */
	@Async
	public Future<User> findByEmailIdIgnoreCase(String emailId);

	/**
	 * findByIsEnabled
	 * 
	 * @param isEnabled
	 * @return Future<List<User>>
	 */
	@Async
	public Future<List<User>> findByIsEnabled(Boolean isEnabled);

	/**
	 * findByEmailIdOrMobileNumber
	 * 
	 * @param emailId
	 * @param mobileNumber
	 * @return Future<User>
	 */
	@Async
	public Future<User> findByEmailIdOrMobileNumber(String emailId, String mobileNumber);

	/**
	 * findByMobileNumber
	 * 
	 * @param mobileNumber
	 * @return Future<User>
	 */
	@Async
	public Future<User> findByMobileNumber(String mobileNumber);

	/**
	 * findById
	 * 
	 * @param id
	 * @return Future<User>
	 */
	@Async
	public Future<User> findById(String id);

	/**
	 * findByEmailIdIgnoreCaseAndPassword
	 * 
	 * @param emailId
	 * @param password
	 * @return Future<User>
	 */
	@Async
	public Future<User> findByEmailIdIgnoreCaseAndPassword(String emailId, String password);
}
