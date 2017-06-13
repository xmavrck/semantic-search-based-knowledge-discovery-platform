package com.sarah.semantic_analysis.web_oauth_server.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarah.semantic_analysis.web_oauth_server.dao.TempTokenDao;
import com.sarah.semantic_analysis.web_oauth_server.dto.OAuthRequestTokenParams;
import com.sarah.semantic_analysis.web_oauth_server.entities.OAuthTemp;
import com.sarah.semantic_analysis.web_oauth_server.repositories.TempTokenRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.TempTokenService;
import com.sarah.semantic_analysis.web_oauth_server.utils.AESencrp;
import com.sarah.semantic_analysis.web_oauth_server.utils.OAuthUtil;

/**
 * The class MvcConfiguration.
 * 
 * @author chandan
 */
@Service
public class TempTokenServiceImpl implements TempTokenService {

	/**
	 * tempTokenRepo
	 */
	@Autowired
	public TempTokenRepo tempTokenRepo;
	/**
	 * tempTokenDao
	 */
	@Autowired
	public TempTokenDao tempTokenDao;

	/**
	 * aeSencrp
	 */
	@Autowired
	public AESencrp aeSencrp;
	/**
	 * oAuthUtils
	 */
	@Autowired
	public OAuthUtil oAuthUtils;

	/**
	 * verifiyRequestToken
	 * 
	 * @param nonce
	 * @param timestamp
	 * @return boolean
	 */
	public boolean verifiyRequestToken(String nonce, String timestamp) {
		try {
			Future<OAuthTemp> result = tempTokenRepo.findByNonceAndTimestamp(nonce,
					new Date(Long.parseLong(timestamp)));
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			return result.get() != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * updateOAuthVerifier
	 * 
	 * @param oAuthToken
	 * @param userId
	 * @return OAuthTemp
	 * @throws Exception
	 */
	public OAuthTemp updateOAuthVerifier(String oAuthToken, String userId) throws Exception {
		try {
			Future<OAuthTemp> result = tempTokenRepo.findByOAuthToken(oAuthToken);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			OAuthTemp oAuthTemp = result.get();
			if (oAuthTemp != null) {
				oAuthTemp.setUserId(userId);
				oAuthTemp.setoAuthVerified(aeSencrp.encrypt(oAuthToken + userId));
				return tempTokenRepo.save(oAuthTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * validateOAuthVerifier
	 * 
	 * @param oAuthToken
	 * @param oAuthVerifier
	 * @param consumerKey
	 * @return String
	 * @throws Exception
	 */
	public String validateOAuthVerifier(String oAuthToken, String oAuthVerifier, String consumerKey) throws Exception {
		oAuthVerifier = oAuthVerifier.replaceAll(" ", "+");
		try {

			Future<List<OAuthTemp>> result = tempTokenRepo.findByOAuthTokenAndOAuthVerifiedAndConsumerKey(
					oAuthToken.trim(), oAuthVerifier.trim(), consumerKey);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			List<OAuthTemp> oAuthTemp = result.get();
			if (oAuthTemp != null && oAuthTemp.size() > 0) {
				return oAuthTemp.get(0).getUserId() + "";
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * deleteTemporaryToken
	 * 
	 * @param oAuthToken
	 * @param oAuthVerifier
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteTemporaryToken(String oAuthToken, String oAuthVerifier) throws Exception {
		try {
			Future<Long> result = tempTokenRepo.deleteByOAuthTokenAndOAuthVerified(oAuthToken, oAuthVerifier);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			Long value = result.get();
			if (value != null)
				return value > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * getCallBackUrl
	 * 
	 * @param oauth_token
	 * @return String
	 */
	public String getCallBackUrl(String oauth_token) {
		try {
			Future<OAuthTemp> result = tempTokenRepo.findByOAuthToken(oauth_token);
			while (!result.isDone()) {
				Thread.sleep(10);
			}
			OAuthTemp temp = result.get();
			if (temp != null) {
				return temp.getCallBackUrl();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * saveTempToken
	 * 
	 * @param params
	 * @return String[]
	 */
	public String[] saveTempToken(OAuthRequestTokenParams params) {
		try {
			String oAuthToken = URLEncoder.encode(oAuthUtils.generateTempOAuthToken(), "UTF-8");
			System.out.println("OAUth CAllback  " + params.getOauth_callback());
			String oAuthTokenSecret = URLEncoder.encode(
					aeSencrp.encrypt(oAuthToken + params.getOauth_consumer_key() + params.getOauth_callback()),
					"UTF-8");
			if (tempTokenRepo.save(getObject(params, oAuthToken, oAuthTokenSecret)) != null) {
				return new String[] { oAuthToken, oAuthTokenSecret };
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getObject
	 * 
	 * @param params
	 * @param oAuthToken
	 * @param oAuthTokenSecret
	 * @return OAuthTemp
	 */
	OAuthTemp getObject(OAuthRequestTokenParams params, String oAuthToken, String oAuthTokenSecret) {
		OAuthTemp obj = new OAuthTemp();
		obj.setoAuthToken(oAuthToken);
		obj.setCallBackUrl(params.getOauth_callback());
		obj.setConsumerKey(params.getOauth_consumer_key());
		obj.setoAuthTokenSecret(oAuthTokenSecret);
		obj.setNonce(params.getOauth_nonce());
		obj.setTimestamp(new Date(Long.parseLong(params.getOauth_timestamp())));
		return obj;
	}

	/**
	 * findByNonceAndConsumerKey
	 * 
	 * @param nonce
	 * @param consumerKey
	 * @return OAuthTemp
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public OAuthTemp findByNonceAndConsumerKey(String nonce, String consumerKey)
			throws InterruptedException, ExecutionException {
		Future<OAuthTemp> tempRes = tempTokenRepo.findByNonceAndConsumerKey(nonce, consumerKey);
		while (!tempRes.isDone()) {
		}
		OAuthTemp temp = tempRes.get();
		return temp;
	}

	/**
	 * findByConsumerKey
	 * 
	 * @param consumerKey
	 * @return OAuthTemp
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public OAuthTemp findByConsumerKey(String consumerKey) throws InterruptedException, ExecutionException {
		Future<OAuthTemp> tempRes = tempTokenRepo.findByConsumerKey(consumerKey);
		while (!tempRes.isDone()) {
		}
		OAuthTemp temp = tempRes.get();
		return temp;
	}

	/**
	 * deleteTempToken
	 * 
	 * @param consumerKey
	 * @param userId
	 * @return Integer
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	public Integer deleteTempToken(String consumerKey, String userId) throws InterruptedException, ExecutionException {
		Future<Integer> tempRes = tempTokenDao.deleteTempToken(consumerKey, userId);
		while (!tempRes.isDone()) {
		}
		Integer temp = tempRes.get();
		return temp;
	}
}
