package org.sarah.web.client.oauthclient2.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.ClientProtocolException;
import org.sarah.web.client.oauthclient2.types.AccessTokenRequest;
import org.sarah.web.client.oauthclient2.types.AccessTokenResponse;

/**
 * The class OAuthHttp.
 * 
 * @author chandan
 */
public interface OAuthHttp {
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
	AccessTokenResponse getAccessToken(String apiUrl,String oAuth2Provider,AccessTokenRequest request) throws ClientProtocolException, IOException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, NullPointerException;
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
	AccessTokenResponse checkAccessToken(AccessTokenRequest request) throws ClientProtocolException, IOException,
			URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, NullPointerException;

}
