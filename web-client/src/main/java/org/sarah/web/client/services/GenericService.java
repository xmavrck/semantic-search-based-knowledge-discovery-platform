package org.sarah.web.client.services;

import java.io.Serializable;
import java.util.List;

import org.sarah.web.client.dto.Response;

/**
 * The class GenericService.
 * 
 * @author chandan
 */
public interface GenericService<T> {
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
	 * @return Response
	 */
	public Response save(T domain);

	/**
	 * update
	 * 
	 * @param domain
	 * @return Response
	 */
	public Response update(T domain);

	/**
	 * delete
	 * 
	 * @param id
	 * @return Response
	 */
	public Response delete(Serializable id);

	/**
	 * get
	 * 
	 * @param id
	 * @return T
	 */
	public T get(Serializable id);

}
