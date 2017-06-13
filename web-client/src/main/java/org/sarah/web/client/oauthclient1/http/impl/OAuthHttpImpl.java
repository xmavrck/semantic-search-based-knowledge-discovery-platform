package org.sarah.web.client.oauthclient1.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.sarah.web.client.dto.OAuthParamsDto;
import org.sarah.web.client.oauthclient1.constants.OAuthConstants;
import org.sarah.web.client.oauthclient1.http.OAuthHttp;
import org.sarah.web.client.oauthclient1.types.AccessToken;
import org.sarah.web.client.oauthclient1.types.CheckAccessToken;
import org.sarah.web.client.oauthclient1.types.OAuthRequest;
import org.sarah.web.client.oauthclient1.types.RequestToken;
import org.sarah.web.client.oauthclient1.utils.OAuthUtils;
import org.sarah.web.client.oauthclient1.utils.SignatureUtils;
import org.sarah.web.client.util.ResponseConverterUtil;
import org.sarah.web.client.util.StringUtils;

import com.google.gson.Gson;

/**
 * The class OAuthHttpImpl.
 * 
 * @author chandan
 */
public class OAuthHttpImpl implements OAuthConstants.Vars

		, OAuthHttp, OAuthConstants.Url_OAuth1 {
	/**
	 * gson
	 */
	Gson gson = new Gson();
	/**
	 * responseConverterUtil
	 */
	ResponseConverterUtil responseConverterUtil = new ResponseConverterUtil();

	/**
	 * RESPONSE_TYPE
	 */
	enum RESPONSE_TYPE {
		RAW, JSON
	}

	/**
	 * getOauthTokenAndSecret
	 * 
	 * @param apiUrl
	 * @param callBackUrl
	 * @param consumerKey
	 * @param consumerSecret
	 * @return RequestToken
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public RequestToken getOauthTokenAndSecret(String apiUrl, String callBackUrl, String consumerKey,
			String consumerSecret) throws ClientProtocolException, IOException, URISyntaxException, InvalidKeyException,
			NullPointerException, NoSuchAlgorithmException {

		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		HashMap<String, String> formData = new HashMap<String, String>();

		OAuthParamsDto paramsDto = buildOauthParams(consumerSecret, consumerKey, "POST", null, null);
		formData.put("oauth_consumer_key", consumerKey);
		formData.put("oauth_signature_method", SIGNATURE_METHOD);
		formData.put("oauth_timestamp", paramsDto.getTimestamp());
		formData.put("oauth_nonce", paramsDto.getNonce());
		formData.put("oauth_version", OAUTH_VERSION);
		String urlString = "http://" + apiUrl + REQUEST_TOKEN_PATH;
		String signature = SignatureUtils.getSignature(paramsDto, new URL(urlString), formData);
		qparams.add(new BasicNameValuePair("oauth_consumer_key", consumerKey));
		qparams.add(new BasicNameValuePair("oauth_signature_method", SIGNATURE_METHOD));
		qparams.add(new BasicNameValuePair("oauth_signature", URLEncoder.encode(signature, "UTF-8")));
		qparams.add(new BasicNameValuePair("oauth_timestamp", paramsDto.getTimestamp()));
		qparams.add(new BasicNameValuePair("oauth_nonce", paramsDto.getNonce()));
		qparams.add(new BasicNameValuePair("oauth_version", OAUTH_VERSION));
		qparams.add(new BasicNameValuePair("oauth_callback", callBackUrl));
		HttpPost httpPost = new HttpPost(urlString);
		httpPost.setEntity(new UrlEncodedFormEntity(qparams));

		HttpEntity entity = httpclient.execute(httpPost).getEntity();
		return gson.fromJson(getResponse(entity, RESPONSE_TYPE.JSON), RequestToken.class);
	}

	/**
	 * getAccessToken
	 * 
	 * @param apiUrl
	 * @param oAuthToken
	 * @param oAuthVerifier
	 * @param consumerKey
	 * @param consumerSecret
	 * @return AccessToken
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public AccessToken getAccessToken(String apiUrl, String oAuthToken, String oAuthVerifier, String consumerKey,
			String consumerSecret) throws ClientProtocolException, IOException, URISyntaxException,
			NullPointerException, InvalidKeyException, NoSuchAlgorithmException {
		HttpClient httpclient = new DefaultHttpClient();
		HashMap<String, String> formData = new HashMap<String, String>();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("oauth_verifier", oAuthVerifier));
		formData.put("oauth_verifier", oAuthVerifier);
		String urlString = "http://" + apiUrl + ACCESS_TOKEN_PATH;
		OAuthParamsDto paramsDto = buildOauthParams(consumerSecret, consumerKey, "POST", oAuthToken, null);
		String signature = SignatureUtils.getSignature(paramsDto, new URL(urlString), formData);
		HttpPost httppost = new HttpPost(urlString);
		httppost.setHeader("Authorization", buildAuthorization(consumerKey, String.valueOf(paramsDto.getNonce()),
				signature, String.valueOf(paramsDto.getTimestamp()), oAuthToken, OAUTH_VERSION, true));
		httppost.setEntity(new UrlEncodedFormEntity(qparams));
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		return gson.fromJson(getResponse(entity, RESPONSE_TYPE.JSON), AccessToken.class);
	}

	/**
	 * checkAccessToken
	 * 
	 * @param apiUrl
	 * @param clientUrl
	 * @param clientMethod
	 * @param request
	 * @return CheckAccessToken
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public CheckAccessToken checkAccessToken(String apiUrl, String clientUrl, String clientMethod, OAuthRequest request)
			throws ClientProtocolException, NullPointerException, IOException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException {
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// Service Provider Specific Params
		qparams.add(new BasicNameValuePair("clientUrl", clientUrl));
		qparams.add(new BasicNameValuePair("clientMethod", clientMethod));
		qparams = addParams(qparams, request.getFormData());
		String urlString = "http://" + apiUrl + CHECK_ACCESS_TOKEN_PATH;
		HttpPost httppost = new HttpPost(urlString);
		httppost.setHeader("Authorization",
				buildAuthorization(request.getConsumerKey(), request.getNonce(), request.getSignature(),
						request.getTimestamp(), request.getoAuthToken(), request.getOauthversion(), false));
		httppost.setEntity(new UrlEncodedFormEntity(qparams));
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		return gson.fromJson(getResponse(entity, RESPONSE_TYPE.JSON), CheckAccessToken.class);
	}

	/**
	 * checkAccessToken
	 * 
	 * @param checkAccessToken
	 * @return CheckAccessToken
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public CheckAccessToken checkAccessToken(String apiUrl, OAuthRequest checkAccessToken)
			throws ClientProtocolException, IOException, NullPointerException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException {
		HttpClient httpclient = new DefaultHttpClient();
		HashMap<String, String> formData = new HashMap<String, String>();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		String urlString = "http://" + apiUrl + CHECK_ACCESS_TOKEN_PATH;
		HttpPost httppost = new HttpPost(urlString);
		OAuthParamsDto paramsDto = buildOauthParams(checkAccessToken.getConsumerSecret(),
				checkAccessToken.getConsumerKey(), "POST", checkAccessToken.getoAuthToken(),
				checkAccessToken.getAccessTokenSecret());
		String signature = SignatureUtils.getSignature(paramsDto, new URL(urlString), formData);
		httppost.setHeader("Authorization", buildAuthorization(checkAccessToken.getConsumerKey(), paramsDto.getNonce(),
				signature, paramsDto.getTimestamp(), checkAccessToken.getoAuthToken(), OAUTH_VERSION, true));
		httppost.setEntity(new UrlEncodedFormEntity(qparams));
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		return gson.fromJson(getResponse(entity, RESPONSE_TYPE.JSON), CheckAccessToken.class);
	}

	/**
	 * get
	 * 
	 * @param apiUrl
	 * @param userId
	 * @param path
	 * @param oAuthToken
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessTokenSecret
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public String get(String apiUrl, String userId, String path, String oAuthToken, String consumerKey,
			String consumerSecret, String accessTokenSecret) throws ClientProtocolException, IOException,
			NullPointerException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException {
		HttpClient httpclient = new DefaultHttpClient();
		HashMap<String, String> formData = new HashMap<String, String>();
		String urlString = "http://" + apiUrl + path;
		HttpGet httppost = new HttpGet(urlString);
		OAuthParamsDto paramsDto = buildOauthParams(consumerSecret, consumerKey, "GET", oAuthToken, accessTokenSecret);
		String signature = SignatureUtils.getSignature(paramsDto, new URL(urlString), formData);
		httppost.setHeader("Authorization", buildAuthorization(consumerKey, paramsDto.getNonce(), signature,
				paramsDto.getTimestamp(), oAuthToken, OAUTH_VERSION, true));
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		return getResponse(entity, RESPONSE_TYPE.RAW);
	}

	/**
	 * post
	 * 
	 * @param apiUrl
	 * @param userId
	 * @param path
	 * @param oAuthToken
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessTokenSecret
	 * @param json
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public String post(String apiUrl, String userId, String path, String oAuthToken, String consumerKey,
			String consumerSecret, String accessTokenSecret, String json) throws ClientProtocolException, IOException,
			NullPointerException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException {
		HttpClient httpclient = new DefaultHttpClient();
		HashMap<String, String> formData = new HashMap<String, String>();
		String urlString = "http://" + apiUrl + path;
		HttpPost httppost = new HttpPost(urlString);
		OAuthParamsDto paramsDto = buildOauthParams(consumerSecret, consumerKey, "POST", oAuthToken, accessTokenSecret);
		String signature = SignatureUtils.getSignature(paramsDto, new URL(urlString), formData);
		httppost.setHeader("Authorization", buildAuthorization(consumerKey, paramsDto.getNonce(), signature,
				paramsDto.getTimestamp(), oAuthToken, OAUTH_VERSION, true));
		StringEntity params = new StringEntity(json);
		httppost.addHeader("content-type", "application/json");
		httppost.setEntity(params);
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		return getResponse(entity, RESPONSE_TYPE.RAW);
	}

	/**
	 * buildAuthorization
	 * 
	 * @param consumerKey
	 * @param nonce
	 * @param signature
	 * @param timestamp
	 * @param token
	 * @param oAuthVersion
	 * @param signEncoded
	 * @return String
	 * @throws NullPointerException
	 */
	String buildAuthorization(String consumerKey, String nonce, String signature, String timestamp, String token,
			String oAuthVersion, boolean signEncoded) throws NullPointerException {
		StringBuilder headers = new StringBuilder("OAuth ");
		if (signEncoded) {
			signature = StringUtils.percentEncode(ENC, signature);
		}
		headers.append(StringUtils.percentEncode(ENC, "oauth_consumer_key")).append("=").append("\"")
				.append(StringUtils.percentEncode(ENC, consumerKey)).append("\"").append(", ")
				.append(StringUtils.percentEncode(ENC, "oauth_nonce")).append("=").append("\"")
				.append(StringUtils.percentEncode(ENC, nonce)).append("\"").append(", ")
				.append(StringUtils.percentEncode(ENC, "oauth_signature")).append("=").append("\"").append(signature)
				.append("\"").append(", ").append(StringUtils.percentEncode(ENC, "oauth_signature_method")).append("=")
				.append("\"").append(StringUtils.percentEncode(ENC, SIGNATURE_METHOD)).append("\"").append(", ")
				.append(StringUtils.percentEncode(ENC, "oauth_timestamp")).append("=").append("\"")
				.append(StringUtils.percentEncode(ENC, timestamp)).append("\"").append(",")
				.append(StringUtils.percentEncode(ENC, "oauth_token")).append("=").append("\"")
				.append(StringUtils.percentEncode(ENC, token)).append("\"").append(", ")
				.append(StringUtils.percentEncode(ENC, "oauth_version")).append("=").append("\"")
				.append(StringUtils.percentEncode(ENC, oAuthVersion)).append("\"");
		return headers.toString();
	}

	/**
	 * addParams
	 * 
	 * @param qParams
	 * @param formData
	 * @return List<NameValuePair>
	 */
	List<NameValuePair> addParams(List<NameValuePair> qParams, HashMap<String, String> formData) {
		Iterator<String> itr = formData.keySet().iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			qParams.add(new BasicNameValuePair(key, formData.get(key)));
		}
		return qParams;
	}

	/**
	 * getResponse
	 * 
	 * @param entity
	 * @param responseType
	 * @return String
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	private String getResponse(HttpEntity entity, RESPONSE_TYPE responseType) throws ClientProtocolException,
			IOException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException {
		StringBuffer RESULT = new StringBuffer();
		if (entity != null) {
			InputStream instream = entity.getContent();
			int len;
			byte[] tmp = new byte[BUFFER_SIZE];
			while ((len = instream.read(tmp)) != -1) {
				RESULT.append(new String(tmp, 0, len, ENC));
			}
		}
		if (RESPONSE_TYPE.JSON.equals(responseType)) {
			return gson.toJson(responseConverterUtil.splitToMap(RESULT.toString(), "&", "="));
		} else {
			return RESULT.toString();
		}
	}

	/**
	 * buildOauthParams
	 * 
	 * @param consumerSecret
	 * @param consumerKey
	 * @param method
	 * @param oAuthToken
	 * @param oAuthTokenSecret
	 * @return OAuthParamsDto
	 */
	OAuthParamsDto buildOauthParams(String consumerSecret, String consumerKey, String method, String oAuthToken,
			String oAuthTokenSecret) {
		return new OAuthParamsDto(consumerSecret, consumerKey, OAuthUtils.getOAuthNounce(), SIGNATURE_METHOD, null,
				OAuthUtils.getTimeStamp(), method, OAUTH_VERSION, oAuthToken, null);
	}
}
