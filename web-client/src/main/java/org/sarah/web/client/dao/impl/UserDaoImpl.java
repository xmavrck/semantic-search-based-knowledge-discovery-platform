package org.sarah.web.client.dao.impl;

import java.util.List;
import java.util.concurrent.Future;

import javax.naming.OperationNotSupportedException;

import org.sarah.web.client.dao.UserDao;
import org.sarah.web.client.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * The class UserDaoImpl.
 * 
 * @author chandan
 */
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
	/**
	 * mongoTemplate
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * UserDaoImpl
	 * 
	 */
	public UserDaoImpl() {
		setMongoTemplate(mongoTemplate);
	}

	/**
	 * getMongoTemplate
	 * 
	 * @return MongoTemplate
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * search
	 * 
	 * @return Future<List<User>>
	 * @throws OperationNotSupportedException
	 */
	public Future<List<User>> search() throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
}
