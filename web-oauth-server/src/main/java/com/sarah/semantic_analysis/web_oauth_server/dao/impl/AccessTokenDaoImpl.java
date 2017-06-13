package com.sarah.semantic_analysis.web_oauth_server.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;

import com.sarah.semantic_analysis.web_oauth_server.dao.AccessTokenDao;
import com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken;

/**
 * The class AccessTokenDaoImpl.
 * 
 * @author chandan
 */
@Repository
public class AccessTokenDaoImpl implements AccessTokenDao {
	/**
	 * mongoOperation
	 */
	@Autowired
	private MongoTemplate mongoOperation;

	/**
	 * validateAccessToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Future<AccessToken>
	 */
	public Future<AccessToken> validateAccessToken(String consumerKey, String userId) {
		Query query = new Query();
		List<Criteria> criteriaList = new ArrayList<Criteria>();
		Criteria criteria = new Criteria();
		criteriaList.add(Criteria.where("consumerKey").is(consumerKey));
		criteriaList.add(Criteria.where("userId").is(userId));
		criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
		query.addCriteria(criteria);
		AccessToken obj = mongoOperation.findOne(query, AccessToken.class);
		return new AsyncResult<AccessToken>(obj);
	}

	/**
	 * validateAccessTokenConsumerKey
	 * 
	 * @param consumerKey
	 * @param accessToken
	 * @return Future<AccessToken>
	 */
	public Future<AccessToken> validateAccessTokenConsumerKey(String consumerKey, String accessToken) {
		Query query = new Query();
		List<Criteria> criteriaList = new ArrayList<Criteria>();
		Criteria criteria = new Criteria();
		criteriaList.add(Criteria.where("consumerKey").is(consumerKey));
		criteriaList.add(Criteria.where("accessToken").is(accessToken));

		criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
		query.addCriteria(criteria);
		AccessToken obj = mongoOperation.findOne(query, AccessToken.class);
		return new AsyncResult<AccessToken>(obj);
	}
}
