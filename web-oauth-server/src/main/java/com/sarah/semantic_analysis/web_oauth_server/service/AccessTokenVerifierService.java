package com.sarah.semantic_analysis.web_oauth_server.service;

import java.util.List;

import com.sarah.semantic_analysis.web_oauth_server.entities.AccessTokenVerifiers;

/**
 * The interface AccessTokenVerifierService.
 * 
 * @author chandan
 */
public interface AccessTokenVerifierService extends GenericService<AccessTokenVerifiers> {
	/**
	 * findByAccessTokenAndNonce
	 * 
	 * @param accessToken
	 * @param nonce
	 * @return List<AccessTokenVerifiers>
	 */
	public List<AccessTokenVerifiers> findByAccessTokenAndNonce(String accessToken, String nonce);
}
