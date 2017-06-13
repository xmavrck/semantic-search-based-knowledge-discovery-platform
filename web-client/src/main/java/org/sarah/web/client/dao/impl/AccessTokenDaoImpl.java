package org.sarah.web.client.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.sarah.web.client.dao.AccessTokenDao;
import org.sarah.web.client.entities.AccessTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * The class AccessTokenDaoImpl.
 * 
 * @author chandan
 */
@Repository("accessTokenDao")
public class AccessTokenDaoImpl extends GenericDaoImpl<AccessTokens> implements AccessTokenDao {

	/**
	 * mongoOperation
	 */
	@Autowired
	private MongoTemplate mongoOperation;

	/**
	 * AccessTokenDaoImpl
	 * 
	 */
	public AccessTokenDaoImpl() {
		setMongoTemplate(mongoOperation);
	}

	/**
	 * getMongoTemplate
	 * 
	 * @return MongoTemplate
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoOperation;
	}

	/**
	 * findByAccessToken
	 * 
	 * @param token
	 * @return List<AccessTokens>
	 */
	public List<AccessTokens> findByAccessToken(String token) {
		Query query = new Query();
		List<AccessTokens> list = null;
		try {
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			Criteria criteria = new Criteria();
			criteriaList.add(Criteria.where("accessToken").is(token));
			query.addCriteria(criteria);

			list = mongoOperation.find(query, AccessTokens.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return list;
	}

	/**
	 * findByUserId
	 * 
	 * @param userId
	 * @return List<AccessTokens>
	 */
	public List<AccessTokens> findByUserId(String userId) {
		Query query = new Query();
		List<AccessTokens> list = null;
		try {
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			Criteria criteria = new Criteria();
			criteriaList.add(Criteria.where("userId").is(userId));
			query.addCriteria(criteria);

			list = mongoOperation.find(query, AccessTokens.class);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return list;
	}

	/**
	 * deleteByAccessToken
	 * 
	 * @param token
	 */
	public void deleteByAccessToken(String token) {
		try {
			List<AccessTokens> accessTokens = findByAccessToken(token);
			if (accessTokens != null && accessTokens.size() > 0) {
				mongoOperation.remove(accessTokens.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * deleteByUserId
	 * 
	 * @param userId
	 */
	public void deleteByUserId(String userId) {
		try {
			List<AccessTokens> accessTokens = findByUserId(userId);
			if (accessTokens != null && accessTokens.size() > 0) {
				mongoOperation.remove(accessTokens.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}
