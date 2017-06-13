package org.sarah.web.client.dao;

import java.util.List;
import java.util.concurrent.Future;

import javax.naming.OperationNotSupportedException;

import org.sarah.web.client.entities.User;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * The interface UserDao.
 * 
 * @author chandan
 */
public interface UserDao extends GenericDao<User> {
	/**
	 * search
	 * 
	 * @return Future<List<User>>
	 * @throws OperationNotSupportedException
	 */
	public Future<List<User>> search() throws OperationNotSupportedException;

	/**
	 * getMongoTemplate
	 * 
	 * @return MongoTemplate
	 */
	public MongoTemplate getMongoTemplate();
}
