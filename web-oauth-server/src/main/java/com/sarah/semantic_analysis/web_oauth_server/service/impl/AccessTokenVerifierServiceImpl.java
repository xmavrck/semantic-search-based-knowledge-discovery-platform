package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarah.semantic_analysis.web_oauth_server.entities.AccessTokenVerifiers;
import com.sarah.semantic_analysis.web_oauth_server.repositories.AccessTokenVerifiersRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.AccessTokenVerifierService;

/**
 * The class AccessTokenVerifierServiceImpl.
 * 
 * @author chandan
 */
@Service
public class AccessTokenVerifierServiceImpl implements AccessTokenVerifierService {
	/**
	 * accessTokenDao
	 */
	@Autowired
	protected AccessTokenVerifiersRepo accessTokenDao;

	/**
	 * findByAccessTokenAndNonce
	 * 
	 * @param accessToken
	 * @param nonce
	 * @return List<AccessTokenVerifiers>
	 */
	public List<AccessTokenVerifiers> findByAccessTokenAndNonce(String accessToken, String nonce) {
		try {
			Future<List<AccessTokenVerifiers>> res = accessTokenDao.findByAccessTokenAndNonce(accessToken, nonce);
			while (!res.isDone()) {
			}
			return res.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * saveOrUpdate
	 * 
	 * @param entity
	 * @return Integer
	 */
	public Integer saveOrUpdate(AccessTokenVerifiers entity) {
		throw new RuntimeException("Method not supported");
	}

	/**
	 * getAll
	 * 
	 * @return List<AccessTokenVerifiers>
	 */
	public List<AccessTokenVerifiers> getAll() {
		throw new RuntimeException("Method not supported");
	}

	/**
	 * get
	 * 
	 * @param id
	 * @return AccessTokenVerifiers
	 */
	public AccessTokenVerifiers get(Object id) {
		throw new RuntimeException("Method not supported");
	}

	/**
	 * add
	 * 
	 * @param entity
	 * @return AccessTokenVerifiers
	 */
	public AccessTokenVerifiers add(AccessTokenVerifiers entity) {
		try {
			return accessTokenDao.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * add
	 * 
	 * @param entity
	 * @return List<AccessTokenVerifiers>
	 */
	public List<AccessTokenVerifiers> add(Set<AccessTokenVerifiers> entity) {
		throw new RuntimeException("Method not supported");
	}

	/**
	 * update
	 * 
	 * @param entity
	 * @return AccessTokenVerifiers
	 */
	public AccessTokenVerifiers update(AccessTokenVerifiers entity) {
		throw new RuntimeException("Method not supported");
	}

	/**
	 * remove
	 * 
	 * @param id
	 * @return Integer
	 */
	public Integer remove(Object id) {
		throw new RuntimeException("Method not supported");
	}

}
