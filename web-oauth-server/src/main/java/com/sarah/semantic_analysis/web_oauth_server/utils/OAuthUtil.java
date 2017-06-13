package com.sarah.semantic_analysis.web_oauth_server.utils;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * The class OAuthUtil.
 * 
 * @author chandan
 */
@Component
public class OAuthUtil {
	/**
	 * out
	 */
	StringWriter out = new StringWriter();
	/**
	 * gson
	 */
	Gson gson = new Gson();

	/**
	 * generateTempOAuthToken
	 * 
	 * @return String
	 */
	public String generateTempOAuthToken() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * convertMapToJSON
	 * 
	 * @param obj
	 * @param c
	 * @return String
	 * @throws Exception
	 */
	public String convertMapToJSON(Object obj, Class c) throws Exception {
		return gson.toJson(obj, c);
	}

	/**
	 * convertObjectToNameValuePair
	 * 
	 * @param obj
	 * @param c
	 * @return String
	 */
	public String convertObjectToNameValuePair(Object obj, Class c) {
		ObjectMapper m = new ObjectMapper();
		Map<String, Object> props = m.convertValue(obj, Map.class);
		return convertMapToNameValuePair(props);
	}

	/**
	 * getNameValuePairFromQueryString
	 * 
	 * @param url
	 * @return Map<String, List<String>>
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, List<String>> getNameValuePairFromQueryString(URL url) throws UnsupportedEncodingException {
		final Map<String, List<String>> query_pairs = new LinkedHashMap<String, List<String>>();
		if (url != null) {
			String query = url.getQuery();
			if (query != null) {
				final String[] pairs = query.split("&");
				for (String pair : pairs) {
					final int idx = pair.indexOf("=");
					final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
					if (!query_pairs.containsKey(key)) {
						query_pairs.put(key, new LinkedList<String>());
					}
					final String value = idx > 0 && pair.length() > idx + 1
							? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
					query_pairs.get(key).add(value);
				}
			}
		}
		return query_pairs;
	}

	/**
	 * getTimeStamp
	 * 
	 * @return String
	 */
	public static String getTimeStamp() {
		return "" + (System.currentTimeMillis() / 1000);
	}

	/**
	 * getOAuthNounce
	 * 
	 * @return String
	 */
	public static String getOAuthNounce() {
		return "" + (int) (Math.random() * 100000000);
	}

	/**
	 * convertMapToNameValuePair
	 * 
	 * @param props
	 * @return String
	 */
	public String convertMapToNameValuePair(Map<String, Object> props) {
		StringBuilder querySring = new StringBuilder();
		List<String> keys = new ArrayList<String>(props.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object value = props.get(key);
			querySring.append(key).append("=").append(value);
			if (i != keys.size() - 1) {
				querySring.append("&");
			}
		}
		return querySring.toString();
	}
}
