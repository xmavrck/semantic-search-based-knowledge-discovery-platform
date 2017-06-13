package org.sarah.web.client.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * The interface GenericDao.
 * 
 * @author chandan
 */
public interface GenericDao<T> {
	/**
	 * loadAll
	 * 
	 * @return List<T>
	 */
	public List<T> loadAll();

	/**
	 * save
	 * 
	 * @param domain
	 */
	public void save(T domain);

	/**
	 * update
	 * 
	 * @param domain
	 */
	public void update(T domain);

	/**
	 * delete
	 * 
	 * @param id
	 */
	public void delete(Serializable id);

	/**
	 * get
	 * 
	 * @param id
	 * @return T
	 */
	public T get(Serializable id);

	/**
	 * setMongoTemplate
	 * 
	 * @param mongoTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate);

}
