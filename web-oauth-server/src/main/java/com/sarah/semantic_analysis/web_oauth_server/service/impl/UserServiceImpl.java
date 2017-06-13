package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mysema.query.types.expr.BooleanExpression;
import com.sarah.semantic_analysis.web_oauth_server.dto.SearchUserDto;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.entities.UserAccountRecovery;
import com.sarah.semantic_analysis.web_oauth_server.predicates.builder.UserPredicateBuilder;
import com.sarah.semantic_analysis.web_oauth_server.repositories.UserAccountRecoveryRepo;
import com.sarah.semantic_analysis.web_oauth_server.repositories.UserRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.EncryptionUtils;

/**
 * The class UserServiceImpl.
 * 
 * @author chandan
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * userDao
	 */
	@Autowired
	UserRepo userDao;
	/**
	 * userAccountRecoveryDao
	 */
	@Autowired
	UserAccountRecoveryRepo userAccountRecoveryDao;
	/**
	 * mongoTemplate
	 */
	@Autowired
	MongoTemplate mongoTemplate;
	/**
	 * encryptionUtils
	 */
	@Autowired
	EncryptionUtils encryptionUtils;

	/**
	 * search
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return Page<User>
	 * @throws Exception
	 */
	public Page<User> search(SearchUserDto searchUserDto, Pageable pageable) throws Exception {
		BooleanExpression exp = new UserPredicateBuilder().build(searchUserDto);
		System.out.println("Exp  " + exp);
		if (exp != null) {
			return userDao.findAll(exp, pageable);
		} else {
			return userDao.findAll(pageable);
		}
	}

	/**
	 * saveOrUpdate
	 * 
	 * @param entity
	 * @return Integer
	 */
	public Integer saveOrUpdate(User entity) {
		try {
			entity.setPassword(encryptionUtils.encrpt(entity.getPassword()));
			User result = userDao.save(entity);
			return result != null ? 200 : 400;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * getAll
	 * 
	 * @return List<User>
	 */
	public List<User> getAll() {
		try {
			Iterable<User> fieldsIterable = userDao.findAll();
			List<User> fields = Lists.newArrayList(fieldsIterable);
			return fields;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}

	/**
	 * get
	 * 
	 * @param id
	 * @return User
	 */
	public User get(Object id) {
		// TODO Auto-generated method stub
		try {
			User result = userDao.findOne((String) id);
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
	 * @return User
	 * @throws Exception
	 */
	public User add(User entity) throws Exception {
		if (entity.getPassword() != null)
			entity.setPassword(encryptionUtils.encrpt(entity.getPassword()));
		return userDao.save(entity);
	}

	/**
	 * update
	 * 
	 * @param entity
	 * @return User
	 */
	public User update(User entity) {
		try {
			return userDao.save(entity);
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
			userDao.delete((String) id);
			return 200;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * findByUserIdNotIn
	 * 
	 * @param userId
	 * @return Page<User>
	 */
	public Page<User> findByUserIdNotIn(String userId) {
		try {
			List<User> users = userDao.findAll();
			List<User> newusers = new ArrayList<User>();
			if (userId != null) {
				for (User user : users) {
					if (!user.getId().equals(userId)) {
						newusers.add(user);
					}
				}
			}
			return new PageImpl<User>(newusers, null, newusers.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PageImpl<User>(new ArrayList<User>());
	}

	/**
	 * add
	 * 
	 * @param entity
	 * @return List<User>
	 */
	public List<User> add(Set<User> entity) {
		try {
			return userDao.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}

	/**
	 * findByEmailAndPassword
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public User findByEmailAndPassword(String emailId, String password)
			throws InterruptedException, ExecutionException {
		try {
			password = encryptionUtils.encrpt(password);
			Future<User> userRes = userDao.findByEmailIdIgnoreCaseAndPassword(emailId, password);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * findByEmailIdAndLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return User
	 */
	public User findByEmailIdAndLinkKey(String emailId, String linkKey) {
		try {
			List<Criteria> criteriaList = new ArrayList<Criteria>();
			Criteria criteria = new Criteria();
			criteriaList.add(Criteria.where("emailId").is(emailId));
			criteriaList.add(Criteria.where("linkKey").is(linkKey));
			criteriaList.add(Criteria.where("expiryDate").gt(new Date()));
			criteria = criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
			Query query = new Query();
			query.addCriteria(criteria);

			List<User> list = mongoTemplate.find(query, User.class);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * findByEmail
	 * 
	 * @param emailId
	 * @return User
	 */
	public User findByEmail(String emailId) {
		try {
			Future<User> userRes = userDao.findByEmailIdIgnoreCase(emailId);
			while (!userRes.isDone()) {
			}
			return userRes.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * findByEmailIdIgnoreCaseAndPassword
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 */
	public User findByEmailIdIgnoreCaseAndPassword(String emailId, String password) {
		try {
			password = encryptionUtils.encrpt(password);
			Future<User> userRes = userDao.findByEmailIdIgnoreCaseAndPassword(emailId, password);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * findByEmailIdAndUserAccountRecoveryLinkKey
	 * 
	 * @param emailId
	 * @param linkKey
	 * @return User
	 */
	public User findByEmailIdAndUserAccountRecoveryLinkKey(String emailId, String linkKey) {
		try {
			Future<User> userRes = userDao.findByEmailIdIgnoreCase(emailId);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			if (user != null) {
				Future<UserAccountRecovery> userAccRes = userAccountRecoveryDao.findByUserIdAndLinkKey(emailId,
						linkKey);
				while (!userAccRes.isDone()) {
				}
				UserAccountRecovery userAcc = userAccRes.get();
				if (userAcc != null) {
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * updatePassCodeAndPassCodeAddDate
	 * 
	 * @param passCode
	 * @param passCodeAddDate
	 * @param emailId
	 * @return Integer
	 */
	public Integer updatePassCodeAndPassCodeAddDate(String passCode, Date passCodeAddDate, String emailId) {
		// TODO Auto-generated method stub
		try {
			Future<User> userRes = userDao.findByEmailIdIgnoreCase(emailId);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			if (user != null) {
				user.setLinkKey(passCode);
				user.setExpiryDate(passCodeAddDate);
				userDao.save(user);
				return 200;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * findById
	 * 
	 * @param id
	 * @return User
	 */
	public User findById(String id) {
		try {
			Future<User> userRes = userDao.findById(id);
			while (!userRes.isDone()) {
			}
			User user = userRes.get();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * findByIsEnabled
	 * 
	 * @param isEnabled
	 * @return List<User>
	 */
	public List<User> findByIsEnabled(Boolean isEnabled) {
		try {
			Future<List<User>> userRes = userDao.findByIsEnabled(isEnabled);
			while (!userRes.isDone()) {
			}
			return userRes.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * findByMobileNumber
	 * 
	 * @param mobileNumber
	 * @return User
	 */
	public User findByMobileNumber(String mobileNumber) {
		try {
			Future<User> userRes = userDao.findByMobileNumber(mobileNumber);
			while (!userRes.isDone()) {
			}
			return userRes.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
