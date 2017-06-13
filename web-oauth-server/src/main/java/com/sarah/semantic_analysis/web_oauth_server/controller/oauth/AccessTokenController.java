package com.sarah.semantic_analysis.web_oauth_server.controller.oauth;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.StatusBean;
import com.sarah.semantic_analysis.web_oauth_server.entities.AccessTokenVerifiers;
import com.sarah.semantic_analysis.web_oauth_server.service.AccessTokenService;
import com.sarah.semantic_analysis.web_oauth_server.service.AccessTokenVerifierService;
import com.sarah.semantic_analysis.web_oauth_server.service.ConsumerService;
import com.sarah.semantic_analysis.web_oauth_server.service.TempTokenService;
import com.sarah.semantic_analysis.web_oauth_server.utils.AuthorizationHeaderParser;
import com.sarah.semantic_analysis.web_oauth_server.utils.OAuthUtil;
import com.sarah.semantic_analysis.web_oauth_server.utils.SignatureUtils;

/**
 * The class AccessTokenController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping(Constants.Controllers.BASE_URL)
public class AccessTokenController {
	/**
	 * accessTokenService
	 */
	@Autowired
	public AccessTokenService accessTokenService;
	/**
	 * tempTokenService
	 */
	@Autowired
	public TempTokenService tempTokenService;
	/**
	 * consumerService
	 */
	@Autowired
	public ConsumerService consumerService;
	/**
	 *accessTokenVerifierService
	 */
	@Autowired
	public AccessTokenVerifierService accessTokenVerifierService;
	/**
	 * oAuthUtils
	 */
	@Autowired
	public OAuthUtil oAuthUtils;
	/**
	 * decodeSignatureUtils
	 */
	@Autowired
	public SignatureUtils decodeSignatureUtils;
	/**
	 * authorizationHeaderParser
	 */
	@Autowired
	public AuthorizationHeaderParser authorizationHeaderParser;

	/**
	 * sendAccessToken
	 * 
	 * @param oauth_verifier
	 * @param authorization
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/access_token")
	public @ResponseBody String sendAccessToken(@RequestParam("oauth_verifier") String oauth_verifier,
			@RequestHeader("Authorization") String authorization) throws Exception {
		Map<String, Object> response = new HashMap();
		try {
			Map authorizationMap = authorizationHeaderParser.parse(authorization);
			String oAuthToken = String.valueOf(authorizationMap.get("oauth_token"));
			String consumerkey = String.valueOf(authorizationMap.get("oauth_consumer_key"));
			String nonce = String.valueOf(authorizationMap.get("oauth_nonce"));
			String userId = tempTokenService.validateOAuthVerifier(oAuthToken,
					URLDecoder.decode(oauth_verifier, "UTF-8"), consumerkey);
			if (Long.parseLong(
					String.valueOf(authorizationMap.get("oauth_timestamp"))) > (new Date().getTime() / 1000)) {
				response.put("code", "400");
				response.put("status", "Bad Request");
				throw new RuntimeException("Invalid Timestamp");
			}
			if (userId != null) {
				com.sarah.semantic_analysis.web_oauth_server.dto.AccessToken acToken = accessTokenService
						.validateAccessToken(consumerkey, userId);
				AccessTokenVerifiers history = new AccessTokenVerifiers();
				history.setNonce(nonce);
				if (acToken == null) {
					acToken = accessTokenService.saveAccessToken(userId, consumerkey);
					String RESULT = oAuthUtils.convertObjectToNameValuePair(acToken,
							com.sarah.semantic_analysis.web_oauth_server.dto.AccessToken.class);
					history.setAccessToken(acToken.getOauth_token());
					accessTokenVerifierService.add(history);
					return RESULT;
				} else {
					history.setAccessToken(acToken.getOauth_token());
					accessTokenVerifierService.add(history);
					return oAuthUtils.convertObjectToNameValuePair(acToken,
							com.sarah.semantic_analysis.web_oauth_server.dto.AccessToken.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.put("code", "400");
		response.put("status", "Bad Request");
		return oAuthUtils.convertMapToNameValuePair(response);
	}

	/**
	 * checkAccessToken
	 * 
	 * @param authorization
	 * @param userId
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/check_access_token", produces = "application/x-www-form-urlencoded")
	public @ResponseBody String checkAccessToken(@RequestHeader("Authorization") String authorization,
			@RequestParam(value = "userId", required = false) String userId) throws Exception {
		StatusBean bean = null;
		try {
			Map authorizationMap = authorizationHeaderParser.parse(authorization);
			Map response = new HashMap();
			String oAuthToken = String.valueOf(authorizationMap.get("oauth_token"));
			String consumerkey = String.valueOf(authorizationMap.get("oauth_consumer_key"));
			String nonce = String.valueOf(authorizationMap.get("oauth_nonce"));
			if (Long.parseLong(
					String.valueOf(authorizationMap.get("oauth_timestamp"))) > (new Date().getTime() / 1000)) {
				throw new RuntimeException("Invalid Timestamp");
			}
			List<AccessTokenVerifiers> accessTokenVerifiers = accessTokenVerifierService
					.findByAccessTokenAndNonce(oAuthToken, nonce);
			if (accessTokenVerifiers != null && accessTokenVerifiers.size() > 0) {
				throw new RuntimeException("Invalid Nonce");
			}
			com.sarah.semantic_analysis.web_oauth_server.dto.AccessToken result = accessTokenService
					.validateAccessTokenConsumerKeyAccessToken(consumerkey, oAuthToken);
			if (result != null) {
				AccessTokenVerifiers accessTokenVerifier = new AccessTokenVerifiers(oAuthToken, nonce);
				accessTokenVerifierService.add(accessTokenVerifier);
				bean = new StatusBean();
				bean.setCode(200);
				bean.setUserId(result.getUser_id());
				bean.setStatus("OK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bean == null) {
			bean = new StatusBean();
			bean.setCode(401);
			bean.setStatus("UnAuthorized");
		}
		return oAuthUtils.convertObjectToNameValuePair(bean, StatusBean.class);
	}
}