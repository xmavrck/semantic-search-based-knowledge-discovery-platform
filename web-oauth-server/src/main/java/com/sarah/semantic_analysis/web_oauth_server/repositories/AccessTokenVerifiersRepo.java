package com.sarah.semantic_analysis.web_oauth_server.repositories;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.scheduling.annotation.Async;

import com.sarah.semantic_analysis.web_oauth_server.entities.AccessTokenVerifiers;
/**
 * The class AccessTokenVerifiersRepo.
 * 
 * @author chandan
 */
@RepositoryRestResource(collectionResourceRel = "accessTokenVerifiers", path = "accessTokenVerifiers")
public interface AccessTokenVerifiersRepo extends MongoRepository<AccessTokenVerifiers, String> {
	/**
	 * findByAccessTokenAndNonce
	 * 
	 * @param accessToken
	 * @param nonce
	 * @return Future<List<AccessTokenVerifiers>>
	 */
	@Async
	public Future<List<AccessTokenVerifiers>> findByAccessTokenAndNonce(String accessToken, String nonce);
}
