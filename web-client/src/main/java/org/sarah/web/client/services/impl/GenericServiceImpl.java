package org.sarah.web.client.services.impl;

import java.io.Serializable;
import java.util.List;

import org.sarah.web.client.dao.GenericDao;
import org.sarah.web.client.dto.Response;
import org.sarah.web.client.services.GenericService;

/**
 * The class GenericServiceImpl.
 * 
 * @author chandan
 */
public abstract class GenericServiceImpl<T> implements GenericService<T> {
	/**
	 * genericDao
	 */
	GenericDao genericDao;

	/**
	 * setGenericDao
	 * 
	 * @param genericDao
	 */
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	/**
	 * getGenericDao
	 * 
	 * @return GenericDao
	 */
	public GenericDao getGenericDao() {
		return genericDao;
	}

	/**
	 * loadAll
	 * 
	 * @return List<T>
	 */
	public List<T> loadAll() {
		return genericDao.loadAll();
	}

	/**
	 * save
	 * 
	 * @param domain
	 * @return Response
	 */
	public Response save(T domain) {
		try {
			genericDao.save(domain);
			return new Response(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(400);
	}

	/**
	 * update
	 * 
	 * @param domain
	 * @return Response
	 */
	public Response update(T domain) {
		try {
			genericDao.update(domain);
			return new Response(200);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(400);
		}

	}

	/**
	 * delete
	 * 
	 * @param id
	 * @return Response
	 */
	public Response delete(Serializable id) {
		try {
			genericDao.delete(id);
			return new Response(200);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(400);
		}

	}

	/**
	 * get
	 * 
	 * @param id
	 * @return T
	 */
	public T get(Serializable id) {
		T t = null;
		System.out.println(genericDao + "  genDao");
		if (genericDao.get(id) != null) {
			t = (T) genericDao.get(id);
		}
		return t;
	}

}
