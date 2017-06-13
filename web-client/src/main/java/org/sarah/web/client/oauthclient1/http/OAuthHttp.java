package org.sarah.web.client.oauthclient1.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.sarah.web.client.oauthclient1.types.AccessToken;
import org.sarah.web.client.oauthclient1.types.CheckAccessToken;
import org.sarah.web.client.oauthclient1.types.OAuthRequest;
import org.sarah.web.client.oauthclient1.types.RequestToken;

/**
 * The interface OAuthHttp.
 * 
 * @author chandan
 */
public interface OAuthHttp {

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
	RequestToken getOauthTokenAndSecret(String apiUrl, String callBackUrl, String consumerKey, String consumerSecret)
			throws ClientProtocolException, IOException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, NullPointerException;

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
	CheckAccessToken checkAccessToken(String apiUrl, String clientUrl, String clientMethod, OAuthRequest request)
			throws ClientProtocolException, IOException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, NullPointerException;

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
	AccessToken getAccessToken(String apiUrl, String oAuthToken, String oAuthVerifier, String consumerKey,
			String consumerSecret) throws ClientProtocolException, IOException, URISyntaxException, InvalidKeyException,
			NoSuchAlgorithmException, NullPointerException;

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
	CheckAccessToken checkAccessToken(String apiUrl, OAuthRequest request) throws ClientProtocolException, IOException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, NullPointerException;

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
	String get(String apiUrl, String userId, String path, String oAuthToken, String consumerKey, String consumerSecret,
			String accessTokenSecret) throws ClientProtocolException, IOException, URISyntaxException,
			InvalidKeyException, NoSuchAlgorithmException, NullPointerException;

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
	String post(String apiUrl, String userId, String path, String oAuthToken, String consumerKey, String consumerSecret,
			String accessTokenSecret, String json) throws ClientProtocolException, IOException, NullPointerException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException;
}
