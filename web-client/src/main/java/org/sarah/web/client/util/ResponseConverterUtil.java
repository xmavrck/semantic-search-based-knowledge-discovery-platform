package org.sarah.web.client.util;

import java.io.StringWriter;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * The class ResponseConverterUtil.
 * 
 * @author chandan
 */
@Component
public class ResponseConverterUtil {
	/**
	 * gson
	 */
	Gson gson = new Gson();
	/**
	 * out
	 */
	StringWriter out = new StringWriter();

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
	 * makeCollection
	 * 
	 * @param iter
	 * @return <E> Collection<E>
	 */
	public <E> Collection<E> makeCollection(Iterable<E> iter) {
		Collection<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	/**
	 * splitToMap
	 * 
	 * @param source
	 * @param entriesSeparator
	 * @param keyValueSeparator
	 * @return Map<String, String>
	 */
	public Map<String, String> splitToMap(String source, String entriesSeparator, String keyValueSeparator) {
		Map<String, String> map = new HashMap<String, String>();
		String[] entries = source.split(entriesSeparator);
		for (String entry : entries) {
			if (!entry.trim().equals("") && entry.contains(keyValueSeparator)) {
				String[] keyValue = entry.split(keyValueSeparator);
				map.put(keyValue[0], keyValue[1]);
			}
		}
		return map;
	}

	/**
	 * convertObjectToNameValuePair
	 * 
	 * @param obj
	 * @return String
	 */
	public String convertObjectToNameValuePair(Object obj) {
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
	 * convertMapToJson
	 * 
	 * @param map
	 * @return String
	 */
	public String convertMapToJson(Map<String, String> map) {
		String json = gson.toJson(map);
		return json;
	}

	/**
	 * convertObjectToJson
	 * 
	 * @param obj
	 * @return String
	 */
	public String convertObjectToJson(Object obj) {
		String json = gson.toJson(obj);
		return json;
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
