package org.sarah.web.client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * The class SolrRDFUtils.
 * 
 * @author chandan
 */
public class SolrRDFUtils {

	/**
	 * hostName
	 */
	String hostName;
	/**
	 * portNo
	 */
	int portNo;
	/**
	 * sparQLEndpoint
	 */
	String sparQLEndpoint;

	/**
	 * SolrRDFUtils
	 * 
	 * @param hostName
	 * @param portNo
	 * @param sparQLEndpoint
	 */
	public SolrRDFUtils(String hostName, int portNo, String sparQLEndpoint) {
		this.hostName = hostName;
		this.portNo = portNo;
		this.sparQLEndpoint = sparQLEndpoint;
	}

	/**
	 * sparQL
	 * 
	 * @param query
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> sparQL(String query) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(new StringBuilder("http://").append(hostName).append(":").append(portNo)
				.append(sparQLEndpoint).toString());

		// Request parameters and other properties.
		List<org.apache.http.NameValuePair> params = new ArrayList<org.apache.http.NameValuePair>(2);
		params.add(new BasicNameValuePair("q", query));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {

			try (BufferedReader instream = new BufferedReader(new InputStreamReader(entity.getContent()));) {
				StringBuilder result = new StringBuilder();
				String temp = "";
				while ((temp = instream.readLine()) != null) {
					result.append(temp);
				}
				return new Gson().fromJson(result.toString(), new TypeToken<HashMap<String, Object>>() {
				}.getType());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		return null;
	}
}
