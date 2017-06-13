package com.sarah.semantic_analysis.web_oauth_server.service;

import java.util.List;
import java.util.Set;
/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
public interface GenericService<E> {

	public Integer saveOrUpdate(E entity);

	public List<E> getAll();

	public E get(Object id);

	public E add(E entity) throws Exception;

	public List<E> add(Set<E> entity);

	public E update(E entity);

	public Integer remove(Object id);
}
