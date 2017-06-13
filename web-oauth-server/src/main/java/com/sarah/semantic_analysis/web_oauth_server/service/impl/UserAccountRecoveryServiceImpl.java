package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sarah.semantic_analysis.web_oauth_server.dao.UserRecoveryDao;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.entities.UserAccountRecovery;
import com.sarah.semantic_analysis.web_oauth_server.repositories.UserAccountRecoveryRepo;
import com.sarah.semantic_analysis.web_oauth_server.repositories.UserRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.UserAccountRecoveryService;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;

/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
@Service
public class UserAccountRecoveryServiceImpl implements UserAccountRecoveryService {

	/**
	 * userAccountRecoveryDao
	 */
	@Autowired
	UserAccountRecoveryRepo userAccountRecoveryDao;
	/**
	 * userRecoveryDao
	 */
	@Autowired
	UserRecoveryDao userRecoveryDao;
	/**
	 * userDao
	 */
	@Autowired
	UserRepo userDao;
	/**
	 * props
	 */
	@Autowired
	ConfigUtils props;
	/**
	 * mongoTemplate
	 */
	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * saveOrUpdate
	 * 
	 * @param entity
	 * @return Integer
	 */
	public Integer saveOrUpdate(UserAccountRecovery entity) {
		try {
			UserAccountRecovery result = userAccountRecoveryDao.save(entity);
			return result != null ? 200 : 400;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * getAll
	 * 
	 * @return List<UserAccountRecovery>
	 */
	public List<UserAccountRecovery> getAll() {
		try {
			Iterable<UserAccountRecovery> fieldsIterable = userAccountRecoveryDao.findAll();
			List<UserAccountRecovery> fields = Lists.newArrayList(fieldsIterable);
			return fields;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserAccountRecovery>();
	}

	/**
	 * get
	 * 
	 * @param id
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery get(Object id) {
		try {
			UserAccountRecovery result = userAccountRecoveryDao.findOne((Integer) id);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * add
	 * 
	 * @param entity
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery add(UserAccountRecovery entity) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, Integer.parseInt(props.getAccountRecoveryLinkExpiry()));
			entity.setExpiryDate(cal.getTime());
			UserAccountRecovery result = userAccountRecoveryDao.save(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * update
	 * 
	 * @param entity
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery update(UserAccountRecovery entity) {
		try {
			UserAccountRecovery result = userAccountRecoveryDao.save(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * remove
	 * 
	 * @param id
	 * @return Integer
	 */
	public Integer remove(Object id) {
		try {
			userAccountRecoveryDao.delete((Integer) id);
			return 200;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * findByEmailIdAndLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery findByEmailIdAndLinkKey(String emailId, String linkKey) {
		try {
			System.out.println("Emailid " + emailId + "  " + linkKey);
			Future<User> userRes = userDao.findByEmailIdIgnoreCase(emailId);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			Criteria criteria = new Criteria();

			criteriaList.add(Criteria.where("userId").is(user.getId()));
			criteriaList.add(Criteria.where("linkKey").is(linkKey));
			criteriaList.add(Criteria.where("expiryDate").gt(new Date()));
			criteria = criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
			Query query = new Query();
			query.addCriteria(criteria);
			List<UserAccountRecovery> list = mongoTemplate.find(query, UserAccountRecovery.class);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * updateActivationCodeAndLinkKey
	 * 
	 * @param resetKey
	 * @param linkKey
	 * @return Integer
	 */
	public Integer updateActivationCodeAndLinkKey(String resetKey, String linkKey) {
		try {
			Future<List<UserAccountRecovery>> userRes = userAccountRecoveryDao.findByLinkKey(linkKey);
			while (!userRes.isDone()) {
			}
			List<UserAccountRecovery> uarList = userRes.get();
			if (uarList != null && uarList.size() > 0) {
				UserAccountRecovery uar = uarList.get(0);
				uar.setResetKey(resetKey);
				userAccountRecoveryDao.save(uar);
			}
			return 200;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * findByEmailIdAndResetKey
	 * 
	 * @param emailId
	 * @param resetKey
	 * @return UserAccountRecovery
	 */
	public UserAccountRecovery findByEmailIdAndResetKey(String emailId, String resetKey) {
		try {
			Future<User> userRes = userDao.findByEmailIdIgnoreCase(emailId);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			Criteria criteria = new Criteria();
			criteriaList.add(Criteria.where("userId").is(user.getId()));
			criteriaList.add(Criteria.where("resetKey").is(resetKey));
			criteriaList.add(Criteria.where("expiryDate").gt(new Date()));
			criteria = criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
			Query query = new Query();
			query.addCriteria(criteria);
			List<UserAccountRecovery> list = mongoTemplate.find(query, UserAccountRecovery.class);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * add
	 * 
	 * @param entity
	 * @return List<UserAccountRecovery>
	 */
	public List<UserAccountRecovery> add(Set<UserAccountRecovery> entity) {
		try {
			return Lists.newArrayList(userAccountRecoveryDao.save(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * deleteByUsermasterUserId
	 * 
	 * @param userId
	 * @return Long
	 */
	public Long deleteByUsermasterUserId(String userId) {
		try {
			Future<List<UserAccountRecovery>> result = userAccountRecoveryDao.findByUserId(userId);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			List<UserAccountRecovery> list = result.get();
			if (list != null && list.size() > 0) {
				userAccountRecoveryDao.deleteByUserId(userId);
			}
			return 200L;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400L;
	}

	/**
	 * findAndDeleteByUserId
	 * 
	 * @param userId
	 * @return Integer
	 */
	public Integer findAndDeleteByUserId(String userId) {
		try {
			Future<Integer> result = userRecoveryDao.findAndDelete(userId);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			return result.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
