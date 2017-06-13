package org.sarah.web.client.oauthclient2.http.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.sarah.web.client.constants.Constants;
import org.sarah.web.client.oauthclient2.constants.OAuthConstants;
import org.sarah.web.client.oauthclient2.http.OAuthHttp;
import org.sarah.web.client.oauthclient2.types.AccessTokenRequest;
import org.sarah.web.client.oauthclient2.types.AccessTokenResponse;
import org.sarah.web.client.util.ResponseConverterUtil;

import com.google.gson.Gson;

/**
 * The class OAuthHttpImpl.
 * 
 * @author chandan
 */
public class OAuthHttpImpl
		implements OAuthConstants.Vars, Constants.OAuthProvider, OAuthHttp, OAuthConstants.Url_OAuth2 {

	/**
	 * hostName
	 */
	Gson gson = new Gson();
	/**
	 * hostName
	 */
	ResponseConverterUtil responseConverterUtil = new ResponseConverterUtil();

	/**
	 * getAccessToken
	 * 
	 * @param apiUrl
	 * @param oAuth2Provider
	 * @param request
	 * @return AccessTokenResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public AccessTokenResponse getAccessToken(String apiUrl, String oAuth2Provider, AccessTokenRequest request)
			throws ClientProtocolException, IOException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, NullPointerException {
		HttpClient httpclient = new DefaultHttpClient();
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("grant_type", request.getGrant_type()));
		qparams.add(new BasicNameValuePair("code", request.getCode()));
		qparams.add(new BasicNameValuePair("redirect_uri", request.getRedirect_uri()));
		qparams.add(new BasicNameValuePair("client_id", request.getClient_id()));
		qparams.add(new BasicNameValuePair("client_secret", request.getClient_secret()));
		String urlString = null;
		if (oAuth2Provider.equals(LINKEDIN)) {
			urlString = "https://" + apiUrl + LINKEDIN_ACCESS_TOKEN_PATH;
		} else if (oAuth2Provider.equals(FACEBOOK)) {
			urlString = "https://" + apiUrl + ACCESS_TOKEN_PATH;
		}
		System.out.println("ACCESS TOKEN " + urlString);
		HttpPost httppost = new HttpPost(urlString);
		httppost.setEntity(new UrlEncodedFormEntity(qparams));
		HttpEntity entity = httpclient.execute(httppost).getEntity();
		return gson.fromJson(getResponse(entity), AccessTokenResponse.class);
	}
	/**
	 * kafkaUtils
	 * 
	 * @param kafkaTopic
	 * @return KafkaUtils
	 * @throws Exception
	 */
	private String getResponse(HttpEntity entity) throws ClientProtocolException, IOException, URISyntaxException,
			InvalidKeyException, NoSuchAlgorithmException {
		String RESULT = "";
		if (entity != null) {
			InputStream instream = entity.getContent();
			int len;
			byte[] tmp = new byte[BUFFER_SIZE];
			while ((len = instream.read(tmp)) != -1) {
				RESULT = new String(tmp, 0, len, ENC);
			}
		}
		System.out.println(RESULT);
		return RESULT;
	}
	/**
	 * kafkaUtils
	 * 
	 * @param kafkaTopic
	 * @return KafkaUtils
	 * @throws Exception
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
	 * checkAccessToken
	 * 
	 * @param request
	 * @return AccessTokenResponse
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public AccessTokenResponse checkAccessToken(AccessTokenRequest request) throws ClientProtocolException, IOException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, NullPointerException {
		return null;
	}
}
