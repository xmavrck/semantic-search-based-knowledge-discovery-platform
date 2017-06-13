package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.OauthRegParam;
import com.sarah.semantic_analysis.web_oauth_server.entities.ClientApp;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.repositories.ConsumerRepo;
import com.sarah.semantic_analysis.web_oauth_server.repositories.UserRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.ConsumerService;
import com.sarah.semantic_analysis.web_oauth_server.utils.CodeUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EncryptionUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.OAuthUtil;

/**
 * The class ConsumerServiceImpl.
 * 
 * @author chandan
 */
@Service
public class ConsumerServiceImpl implements ConsumerService, Constants.ApiConstants {

	/**
	 * registerAppDao
	 */
	@Autowired
	protected ConsumerRepo registerAppDao;
	/**
	 * userDao
	 */
	@Autowired
	protected UserRepo userDao;
	/**
	 * codeUtils
	 */
	@Autowired
	protected CodeUtils codeUtils;
	/**
	 * props
	 */
	@Autowired
	protected ConfigUtils props;
	/**
	 * oAuthUtil
	 */
	@Autowired
	protected OAuthUtil oAuthUtil;
	/**
	 * encryptionUtils
	 */
	@Autowired
	private EncryptionUtils encryptionUtils;
	/**
	 * timeFormat4
	 */
	private static final DecimalFormat timeFormat4 = new DecimalFormat("0000;0000");

	/**
	 * findByConsumerKey
	 * 
	 * @param consumerKey
	 * @return ClientApp
	 */
	public ClientApp findByConsumerKey(String consumerKey) {
		try {
			System.out.println("############consumer key  " + consumerKey);
			Future<ClientApp> result = registerAppDao.findByConsumerKey(consumerKey);
			while (!result.isDone()) {
				Thread.sleep(10);
			}

			ClientApp app = result.get();
			if (app != null) {
				return app;
			} else {
				throw new RuntimeException("Invalid Consumer Key");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * validateConsumerKey
	 * 
	 * @param consumerKey
	 * @return String
	 */
	public String validateConsumerKey(String consumerKey) {
		try {
			System.out.println("############consumer key  " + consumerKey);
			Future<ClientApp> result = registerAppDao.findByConsumerKey(consumerKey);
			while (!result.isDone()) {
				Thread.sleep(10);
			}

			ClientApp app = result.get();
			if (app != null) {
				return app.getConsumerSecret();
			} else {
				throw new RuntimeException("Invalid Consumer Key");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * saveClientApp
	 * 
	 * @param clientApp
	 * @return Object
	 */
	public Object saveClientApp(OauthRegParam clientApp) {
		Map<String, Object> RESPONSE = new HashMap<String, Object>();
		Object responseObject = null;
		try {
			User user = userDao.findOne(clientApp.getDeveloperId());
			if (user != null) {
				String consumer_key = generateConsumerKey(clientApp.getAppName());
				String consumerSecret = generateConsumerSecret();
				if (registerAppDao.save(getObject(clientApp, consumer_key, consumerSecret)) != null) {
					RESPONSE.put("consumer_key", consumer_key);
					RESPONSE.put("consumer_secret", consumerSecret);
					RESPONSE.put("callback_url", clientApp.getCallBackUrl());
					RESPONSE.put("temp_token_uri", "http://" + props.getSemanticAnalysisOauthHostname() + "/"
							+ props.getSemanticAnalysisOAuthBaseUrl() + "/" + REQUEST_TOKEN_PATH);
					RESPONSE.put("auth_uri", "http://" + props.getSemanticAnalysisOauthHostname() + "/"
							+ props.getSemanticAnalysisOAuthBaseUrl() + "/" + AUTHENTICATE_PATH);
					RESPONSE.put("token_uri", "http://" + props.getSemanticAnalysisOauthHostname() + "/"
							+ props.getSemanticAnalysisOAuthBaseUrl() + "/" + ACCESS_TOKEN_PATH);
				} else {
					RESPONSE.put("code", 400);
					RESPONSE.put("error", "Internal Error Occured");
				}
			} else {
				RESPONSE.put("code", 400);
				RESPONSE.put("error", "Developer Id not found or account is not activated yet");
			}
		} catch (Exception e) {
			RESPONSE.put("code", 209);
			RESPONSE.put("error", "Sorry,This app is already registered");
			e.printStackTrace();
		}
		responseObject = oAuthUtil.convertMapToNameValuePair(RESPONSE);
		return responseObject;
	}

	/**
	 * generateConsumerKey
	 * 
	 * @param appName
	 * @return String
	 */
	private synchronized String generateConsumerKey(String appName) {
		Calendar cal = Calendar.getInstance();
		String val = String.valueOf(cal.get(Calendar.YEAR));
		val += timeFormat4.format(cal.get(Calendar.DAY_OF_YEAR));
		val += encryptionUtils.getToken();
		val += cal.getTimeInMillis();
		val += UUID.randomUUID().toString().replaceAll("-", "");
		return val;
	}

	/**
	 * generateConsumerSecret
	 * 
	 * @return String
	 */
	private synchronized String generateConsumerSecret() {
		Calendar cal = Calendar.getInstance();
		String val = encryptionUtils.getToken();
		val += cal.getTimeInMillis();
		val += UUID.randomUUID().toString().replaceAll("-", "");
		return val;
	}

	/**
	 * getObject
	 * 
	 * @param clientApp
	 * @param consumer_key
	 * @param consumerSecret
	 * @return ClientApp
	 */
	ClientApp getObject(OauthRegParam clientApp, String consumer_key, String consumerSecret) {
		ClientApp obj = new ClientApp();
		obj.setAppDesription(clientApp.getDescription());
		obj.setAppName(clientApp.getAppName());
		obj.setAppUrl(clientApp.getAppUrl());
		obj.setCallBackUrl(clientApp.getCallBackUrl());
		obj.setConsumerKey(consumer_key);
		obj.setConsumerSecret(consumerSecret);
		obj.setUserId(clientApp.getDeveloperId());
		return obj;
	}

	/**
	 * findByUserId
	 * 
	 * @param userId
	 * @return List<ClientApp>
	 */

	public List<ClientApp> findByUserId(String userId) {
		try {
			Future<List<ClientApp>> result = registerAppDao.findByUserId(userId);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			return result.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getAll
	 * 
	 * @return List<ClientApp>
	 */
	public List<ClientApp> getAll() {
		throw new UnsupportedOperationException();

	}

	/**
	 * get
	 * 
	 * @param id
	 * @return ClientApp
	 */
	public ClientApp get(Object id) {
		try {
			ClientApp result = registerAppDao.findOne((String) id);
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
	 * @return ClientApp
	 */
	public ClientApp update(ClientApp entity) {
		try {
			return registerAppDao.save(entity);
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
			ClientApp clientApp = registerAppDao.findOne(String.valueOf(id));
			if (clientApp == null) {
				throw new RuntimeException();
			}
			registerAppDao.delete((String) id);
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
	 * @return List
	 */
	public List add(Set entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * saveOrUpdate
	 * 
	 * @param entity
	 * @return Integer
	 */
	public Integer saveOrUpdate(ClientApp entity) {
		try {
			ClientApp clientApp = registerAppDao.findOne(entity.getId());
			if (clientApp == null) {
				throw new RuntimeException();
			}
			entity.setConsumerKey(clientApp.getConsumerKey());
			entity.setConsumerSecret(clientApp.getConsumerSecret());
			ClientApp result = registerAppDao.save(entity);
			return result != null ? 200 : 400;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 400;
	}

	/**
	 * add
	 * 
	 * @param entity
	 * @return ClientApp
	 * @throws Exception
	 */
	public ClientApp add(ClientApp entity) throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * findByAppName
	 * 
	 * @param appName
	 * @return ClientApp
	 */
	public ClientApp findByAppName(String appName) {
		try {
			Future<ClientApp> result = registerAppDao.findByAppName(appName);
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
