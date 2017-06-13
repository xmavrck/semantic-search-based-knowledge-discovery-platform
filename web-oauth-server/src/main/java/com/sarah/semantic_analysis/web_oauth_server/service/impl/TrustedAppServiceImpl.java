package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sarah.semantic_analysis.web_oauth_server.entities.TrustedApps;
import com.sarah.semantic_analysis.web_oauth_server.repositories.TrustedAppsRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.TrustedAppService;

/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
@Service
public class TrustedAppServiceImpl implements TrustedAppService {

	/**
	 * userDao
	 */
	@Autowired
	TrustedAppsRepo userDao;

	/**
	 * saveOrUpdate
	 * 
	 * @param entity
	 * @return Integer
	 */
	public Integer saveOrUpdate(TrustedApps entity) {
		try {
			TrustedApps result = userDao.save(entity);
			return result != null ? 200 : 400;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * getAll
	 * 
	 * @return List<TrustedApps>
	 */
	public List<TrustedApps> getAll() {
		try {
			Iterable<TrustedApps> fieldsIterable = userDao.findAll();
			List<TrustedApps> fields = Lists.newArrayList(fieldsIterable);
			return fields;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TrustedApps>();
	}

	/**
	 * get
	 * 
	 * @param id
	 * @return TrustedApps
	 */
	public TrustedApps get(Object id) {
		// TODO Auto-generated method stub
		try {
			TrustedApps result = userDao.findOne((String) id);
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
	 * @return TrustedApps
	 */
	public TrustedApps add(TrustedApps entity) {
		try {

			return userDao.save(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * update
	 * 
	 * @param entity
	 * @return TrustedApps
	 */
	public TrustedApps update(TrustedApps entity) {
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
	 * add
	 * 
	 * @param entity
	 * @return List<TrustedApps>
	 */
	public List<TrustedApps> add(Set<TrustedApps> entity) {
		try {
			return userDao.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TrustedApps>();
	}

}
