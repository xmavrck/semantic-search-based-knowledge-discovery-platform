package com.sarah.semantic_analysis.web_oauth_server.controller.oauth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.OAuthParamsDto;
import com.sarah.semantic_analysis.web_oauth_server.dto.OAuthRequestTokenParams;
import com.sarah.semantic_analysis.web_oauth_server.dto.RequestTokenResult;
import com.sarah.semantic_analysis.web_oauth_server.entities.OAuthTemp;
import com.sarah.semantic_analysis.web_oauth_server.service.ConsumerService;
import com.sarah.semantic_analysis.web_oauth_server.service.TempTokenService;
import com.sarah.semantic_analysis.web_oauth_server.utils.OAuthUtil;
import com.sarah.semantic_analysis.web_oauth_server.utils.SignatureUtils;

/**
 * The class RequestTokenController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping(Constants.Controllers.REQUEST_TOKEN)
public class RequestTokenController {

	/**
	 * tokenService
	 */
	@Autowired
	public TempTokenService tokenService;
	/**
	 * consumerService
	 */
	@Autowired
	public ConsumerService consumerService;
	/**
	 * oAuthUtils
	 */
	@Autowired
	public OAuthUtil oAuthUtils;
	/**
	 * signatureUtils
	 */
	@Autowired
	SignatureUtils signatureUtils;
	/**
	 * tempTokenService
	 */
	@Autowired
	TempTokenService tempTokenService;

	/**
	 * requestToken
	 * 
	 * @param params
	 * @param request
	 * @return String
	 * @throws InterruptedException,
	 *             ExecutionException
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/x-www-form-urlencoded")
	public @ResponseBody String requestToken(OAuthRequestTokenParams params, HttpServletRequest request)
			throws InterruptedException, ExecutionException {
		String RESPONSE = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			OAuthParamsDto oAuthParamsDto = new OAuthParamsDto(params.getOauth_consumer_key(), params.getOauth_nonce(),
					params.getOauth_signature_method(), params.getOauth_signature(), params.getOauth_timestamp(),
					"POST", params.getOauth_version(), null, request.getRequestURL().toString());
			if (Long.parseLong(String.valueOf(oAuthParamsDto.getTimestamp())) > (new Date().getTime() / 1000)) {
				response.put("code", "400");
				response.put("status", "Bad Request");
				throw new RuntimeException("Invalid Timestamp");
			}
			boolean isValidated = signatureUtils.validateSignature(oAuthParamsDto);
			if (isValidated) {
				OAuthTemp validateRequest = tokenService.findByNonceAndConsumerKey(params.getOauth_nonce(),
						params.getOauth_consumer_key());
				if (validateRequest == null) {
					String oath_secret = consumerService.validateConsumerKey(params.getOauth_consumer_key());
					if (oath_secret != null) {
						String[] oAuthTokenAndSecret = tokenService.saveTempToken(params);
						if (oAuthTokenAndSecret != null) {
							RequestTokenResult oauth = new RequestTokenResult();
							oauth.setOauth_token(oAuthTokenAndSecret[0]);
							oauth.setOauth_token_secret(oAuthTokenAndSecret[1]);
							oauth.setOauth_callback_confirmed(true);
							RESPONSE = oAuthUtils.convertObjectToNameValuePair(oauth, RequestTokenResult.class);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (RESPONSE != null) {
			return RESPONSE;
		} else {
			response.put("code", "400");
			response.put("status", "Bad Request");
			return oAuthUtils.convertMapToNameValuePair(response);
		}
	}

}
