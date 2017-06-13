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

import com.sarah.semantic_analysis.web_oauth_server.dao.TempTokenDao;
import com.sarah.semantic_analysis.web_oauth_server.entities.OAuthTemp;

/**
 * The class TempTokenDaoImpl.
 * 
 * @author chandan
 */
@Repository
public class TempTokenDaoImpl implements TempTokenDao {
	/**
	 * mongoOperation
	 */
	@Autowired
	private MongoTemplate mongoOperation;

	/**
	 * deleteTempToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Future<Integer>
	 */
	public Future<Integer> deleteTempToken(String consumerKey, String userId) {
		try {
			Query query = new Query();
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			Criteria criteria = new Criteria();
			criteriaList.add(Criteria.where("consumerKey").is(consumerKey));
			criteriaList.add(Criteria.where("userId").is(userId));
			criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
			query.addCriteria(criteria);
			mongoOperation.remove(query, OAuthTemp.class);
			return new AsyncResult<Integer>(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new AsyncResult<Integer>(400);
	}
}
