//package com.sarah.web_crawler.utils;
//
//import java.io.File;
//import java.io.InputStream;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.FileEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//public class SolrRDFUtils {
//
//	String hostName;
//	int portNo;
//	String solrEndPoint;
//
//	public SolrRDFUtils(String hostName, int portNo, String solrEndPoint) {
//		this.hostName = hostName;
//		this.portNo = portNo;
//		this.solrEndPoint = solrEndPoint;
//	}
//
//	public void writeToSolr(String filePath) throws Exception {
//		String url = new StringBuilder("http://").append(hostName).append(":").append(portNo).append(solrEndPoint)
//				.toString();
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppost = new HttpPost(url);
//		httppost.setHeader("Accept", "application/json");
//
//		httppost.setEntity(new FileEntity(new File(filePath)));
//		HttpResponse response = httpclient.execute(httppost);
//		HttpEntity resEntity = response.getEntity();
//		if (response.getStatusLine().getStatusCode() == 200 && resEntity != null) {
//			InputStream is = resEntity.getContent();
//		}
//	}
//
//}
