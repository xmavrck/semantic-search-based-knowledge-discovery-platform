package org.sarah.web.client.oauthclient1.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.http.client.ClientProtocolException;
import org.sarah.web.client.dto.OAuthParamsDto;

/**
 * The class SignatureUtils.
 * 
 * @author chandan
 */
public class SignatureUtils {
	/**
	 * RAND
	 */
	private static final Random RAND = new Random();

	/**
	 * getSignature
	 * 
	 * @param oAuthParams
	 * @param url
	 * @param formData
	 * @return String
	 *  @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws InvalidKeyException
	 * @throws NullPointerException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSignature(OAuthParamsDto oAuthParams, URL url, HashMap<String, String> formData)
			throws ClientProtocolException, IOException, URISyntaxException, InvalidKeyException,
			NullPointerException, NoSuchAlgorithmException {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("oauth_consumer_key", oAuthParams.getConsumerKey());
		params.put("oauth_nonce", oAuthParams.getNonce());
		params.put("oauth_signature_method", oAuthParams.getSignatureMethod());
		params.put("oauth_timestamp", oAuthParams.getTimestamp());
		if (oAuthParams.getoAuthToken() != null) {
			params.put("oauth_token", oAuthParams.getoAuthToken());
		}
		params.put("oauth_version", oAuthParams.getoAuthVersion());
		String signature = generateSignature(oAuthParams, url, params, formData);
		return signature;
	}

	/**
	 * getFullURL
	 * 
	 * @param request
	 * @param method
	 * @return String
	 */
	public static String getFullURL(HttpServletRequest request, String method) {
		StringBuffer requestURL = new StringBuffer(request.getRequestURL().toString());
		String queryString = request.getQueryString();
		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}

	}

	/**
	 * sortEncodeMap
	 * 
	 * @param src
	 * @return TreeMap<String, String>
	 */
	protected static TreeMap<String, String> sortEncodeMap(Map<String, String> src) {
		TreeMap<String, String> dst = new TreeMap<String, String>();
		for (String k : src.keySet()) {
			if (!k.equals("oauth_callback")) {
				dst.put(percentEncode(k), percentEncode(src.get(k)));
			}
		}
		return dst;
	}

	/**
	 * generateSignature
	 * 
	 * @param oAuthDto
	 * @param url
	 * @param authParams
	 * @param params
	 * @return String
	 * @throws Exception
	 */
	protected static String generateSignature(OAuthParamsDto oAuthDto, URL url, Map<String, String> authParams,
			Map<String, String> params)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		HashMap<String, String> signaturePartials = new HashMap<String, String>();
		if (authParams != null) {
			signaturePartials.putAll(authParams);
		}
		if (params != null) {
			signaturePartials.putAll(params);
		}
		Map<String, String> map = extractUrlParams(url);
		if (map != null) {
			signaturePartials.putAll(map);
		}
		TreeMap<String, String> authSignatureData = sortEncodeMap(signaturePartials);
		StringBuilder paramsSignature = new StringBuilder();
		boolean isFirst = true;
		for (String key : authSignatureData.keySet()) {
			if (isFirst) {
				isFirst = false;
			} else {
				paramsSignature.append("&");
			}
			paramsSignature.append(key);
			paramsSignature.append("=");
			paramsSignature.append(authSignatureData.get(key));
		}

		String signatureKey = percentEncode(oAuthDto.getConsumerSecret()) + "&"
				+ percentEncode(oAuthDto.getoAuthTokenSecret());
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(new SecretKeySpec(signatureKey.getBytes(), oAuthDto.getSignatureMethod()));

		String signatureVal = oAuthDto.getMethod() + "&" + percentEncode(extractUrlBase(url)) + "&"
				+ percentEncode(paramsSignature.toString());
		byte[] byteHMAC = mac.doFinal(signatureVal.getBytes());
		String signature = DatatypeConverter.printBase64Binary(byteHMAC);
		return signature;
	}

	/**
	 * percentEncode
	 * 
	 * @param s
	 * @return String
	 * @throws RuntimeException
	 */
	public static String percentEncode(String s) throws RuntimeException {
		if (s == null) {
			return "";
		}
		try {
			String regEncoded = URLEncoder.encode(s, "UTF-8");
			return regEncoded.replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException wow) {
			wow.printStackTrace();
			throw new RuntimeException(wow.getMessage(), wow);
		}
	}

	/**
	 * extractUrlParams
	 * 
	 * @param url
	 * @return Map<String, String>
	 * @throws UnsupportedEncodingException
	 */
	protected static Map<String, String> extractUrlParams(URL url) throws UnsupportedEncodingException {
		HashMap<String, String> queryParams = new LinkedHashMap<String, String>();
		String query = url.getQuery();
		if (query != null) {
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				String[] data = pair.split("=");
				if (data.length != 2) {
					throw new IllegalArgumentException("URL is not properly formatted");
				}
				queryParams.put(URLDecoder.decode(data[0], "UTF-8"), URLDecoder.decode(data[1], "UTF-8"));
			}
		}
		return queryParams;
	}

	/**
	 * extractUrlBase
	 * 
	 * @param u
	 * @return String
	 */
	protected static String extractUrlBase(URL u) {
		String url = u.toString();
		if (url != null) {
			int queryPosition = url.indexOf("?");
			if (queryPosition <= 0) {
				queryPosition = url.indexOf("#");
			}

			if (queryPosition >= 0) {
				url = url.substring(0, queryPosition);
			}
		}
		return url;
	}
}
