package org.sarah.web.client.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.sarah.web.client.dao.AccessTokenDao;
import org.sarah.web.client.entities.AccessTokens;
import org.sarah.web.client.services.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class AccessTokenServiceImpl.
 * 
 * @author chandan
 */
@Service
public class AccessTokenServiceImpl extends GenericServiceImpl<AccessTokens> implements AccessTokenService {
	/**
	 * accessTokenDao
	 */
	@Autowired
	AccessTokenDao accessTokenDao;

	/**
	 * initialize
	 * 
	 */
	@PostConstruct
	public void initialize() {
		setGenericDao(accessTokenDao);
		getGenericDao().setMongoTemplate(accessTokenDao.getMongoTemplate());
	}

	/**
	 * findByAccessToken
	 * 
	 * @param sessionId
	 * @return List<AccessTokens>
	 */
	public List<AccessTokens> findByAccessToken(String sessionId) {
		return accessTokenDao.findByAccessToken(sessionId);
	}

	/**
	 * deleteByUserId
	 * 
	 * @param userId
	 */
	public void deleteByUserId(String userId) {
		accessTokenDao.deleteByUserId(userId);
	}

	/**
	 * deleteByAccessToken
	 * 
	 * @param sessionId
	 */
	public void deleteByAccessToken(String sessionId) {
		accessTokenDao.deleteByAccessToken(sessionId);
	}
}
