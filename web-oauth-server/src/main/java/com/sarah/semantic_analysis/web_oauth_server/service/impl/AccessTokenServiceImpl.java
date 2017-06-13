package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarah.semantic_analysis.web_oauth_server.dao.AccessTokenDao;
import com.sarah.semantic_analysis.web_oauth_server.dao.TempTokenDao;
import com.sarah.semantic_analysis.web_oauth_server.dto.AccessToken;
import com.sarah.semantic_analysis.web_oauth_server.repositories.AccessTokenRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.AccessTokenService;
import com.sarah.semantic_analysis.web_oauth_server.utils.AESencrp;
import com.sarah.semantic_analysis.web_oauth_server.utils.EncryptionUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.OAuthUtil;

/**
 * The class AccessTokenServiceImpl.
 * 
 * @author chandan
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

	/**
	 * accessTokenDao
	 */
	@Autowired
	protected AccessTokenDao accessTokenDao;
	/**
	 * tempTokenDao
	 */
	@Autowired
	protected TempTokenDao tempTokenDao;
	/**
	 * accessTokenRepo
	 */
	@Autowired
	protected AccessTokenRepo accessTokenRepo;

	/**
	 * oAuthUtils
	 */
	@Autowired
	protected OAuthUtil oAuthUtils;
	/**
	 * aeSencrp
	 */
	@Autowired
	protected AESencrp aeSencrp;
	/**
	 * encryptionUtils
	 */
	@Autowired
	protected EncryptionUtils encryptionUtils;

	/**
	 * validateAccessTokens
	 * 
	 * @param accessToken
	 * @param consumerKey
	 * @param accessTokenSecret
	 * @return AccessToken
	 */
	public com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken validateAccessTokens(String accessToken,
			String consumerKey, String accessTokenSecret) {
		try {
			Future<com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken> result = accessTokenRepo
					.findByAccessTokenAndTokenSecretAndConsumerKey(accessToken, accessTokenSecret, consumerKey);
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
	 * expireAccessToken
	 * 
	 * @param accessToken
	 * @param accessTokenSecret
	 * @param consumerKey
	 * @return boolean
	 */
	public boolean expireAccessToken(String accessToken, String accessTokenSecret, String consumerKey) {
		try {
			Future<com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken> result = accessTokenRepo
					.findByAccessTokenAndTokenSecretAndConsumerKey(accessToken, accessTokenSecret, consumerKey);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken token = result.get();
			if (token != null) {
				token.setAccessToken("");
				token.setUserId("");
				token.setTokenSecret("");
				token.setConsumerKey("");
				accessTokenRepo.save(token);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * saveAccessToken
	 * 
	 * @param userId
	 * @param consumerKey
	 * @return AccessToken
	 */
	public AccessToken saveAccessToken(String userId, String consumerKey) {
		Map result = new HashMap();
		AccessToken token = new AccessToken();
		try {
			String accessToken = generateAccessToken();
			String secret = generateAccessTokenSecret();
			com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken accessoken = new com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken(
					accessToken, secret, consumerKey, userId);
			try {
				com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken resultDao = accessTokenRepo
						.save(accessoken);
				if (resultDao != null) {
					token.setOauth_token(accessToken);
					token.setOauth_token_secret(secret);
					token.setUser_id(userId);
					token.setCode(200);
					token.setStatus("OK");
					tempTokenDao.deleteTempToken(consumerKey, userId);
				} else {
					token.setCode(400);
					token.setStatus("Internal Error Occured");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			token.setCode(400);
			token.setStatus("Internal Error Occured");
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * generateAccessToken
	 * 
	 * @return String
	 */
	private synchronized String generateAccessToken() {
		Calendar cal = Calendar.getInstance();
		String val = String.valueOf(cal.get(Calendar.YEAR));
		val += encryptionUtils.getToken();
		val += cal.getTimeInMillis();
		val += UUID.randomUUID().toString().replaceAll("-", "");
		return val;
	}

	/**
	 * generateAccessTokenSecret
	 * 
	 * @return String
	 */
	private synchronized String generateAccessTokenSecret() {
		Calendar cal = Calendar.getInstance();
		String val = encryptionUtils.getToken();
		val += cal.getTimeInMillis();
		val += UUID.randomUUID().toString().replaceAll("-", "");
		return val;
	}

	/**
	 * validateAccessToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return AccessToken
	 */
	public AccessToken validateAccessToken(String consumerKey, String userId) {
		try {
			Future<com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken> result = accessTokenDao
					.validateAccessToken(consumerKey, userId);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken obj = result.get();
			if (obj != null) {
				return new AccessToken(obj.getAccessToken(), obj.getUserId(), obj.getTokenSecret(), "OK", 200);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * validateAccessTokenConsumerKeyAccessToken
	 * 
	 * @param consumerKey
	 * @param accessToken
	 * @return AccessToken
	 */
	public AccessToken validateAccessTokenConsumerKeyAccessToken(String consumerKey, String accessToken) {
		try {
			Future<com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken> result = accessTokenDao
					.validateAccessTokenConsumerKey(consumerKey, accessToken);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			com.sarah.semantic_analysis.web_oauth_server.entities.AccessToken obj = result.get();
			if (obj != null) {
				return new AccessToken(obj.getAccessToken(), obj.getUserId(), obj.getTokenSecret(), "OK", 200);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * getAccessTokenSecret
	 * 
	 * @param appName
	 * @param consumerKey
	 * @return String
	 */
	private String getAccessTokenSecret(String appName, String consumerKey) {
		Calendar cal = Calendar.getInstance();
		String val = appName;
		val += consumerKey;
		val += cal.getTimeInMillis();
		val += UUID.randomUUID().toString().replaceAll("-", "");
		return val;
	}
}
