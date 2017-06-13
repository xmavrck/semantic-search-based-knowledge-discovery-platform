package org.sarah.web.client.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.sarah.web.client.dao.GenericDao;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * The class MongoConfiguration.
 * 
 * @author chandan
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
	/**
	 * mongoTemplate
	 */
	private MongoTemplate mongoTemplate;
	/**
	 * entityClass
	 */
	protected Class<T> entityClass;

	/**
	 * GenericDaoImpl
	 */
	public GenericDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * setMongoTemplate
	 * 
	 * @param mongoTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * loadAll
	 * 
	 * @return List<T>
	 */
	public List<T> loadAll() {
		List<T> list = null;
		try {
			list = mongoTemplate.findAll(entityClass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return list;
	}

	/**
	 * save
	 * 
	 * @param domain
	 */
	public void save(T domain) {
		try {
			System.out.println("Mongtemp save " + mongoTemplate);
			mongoTemplate.save(domain);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * update
	 * 
	 * @param domain
	 */
	public void update(T domain) {
		try {
			mongoTemplate.save(domain);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * delete
	 * 
	 * @param id
	 */
	public void delete(Serializable id) {
		try {
			mongoTemplate.remove(get(id));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * get
	 * 
	 * @param id
	 * @return T
	 */
	public T get(Serializable id) {
		T object = null;
		Query query = new Query();
		try {
			query.addCriteria(Criteria.where("id").is(id));
			object = mongoTemplate.findOne(query, entityClass);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return object;
	}
}
