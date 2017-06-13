package com.sarah.realtime.spark.data_processor.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.client.OkClient;

/**
 * The class RetrofitBuilder.
 * 
 * @author chandan
 */
public class RetrofitBuilder implements Serializable {
	/**
	 * CONNECT_TIME_OUT,READ_TIME_OUT,WRITE_TIME_OUT
	 */
	private static final long CONNECT_TIME_OUT = 6500, READ_TIME_OUT = 6500, WRITE_TIME_OUT = 6500;
	/**
	 * classificationMLibUtil
	 */
	ClassificationMLibUtils classificationMLibUtil;

	/**
	 * sparQLUtil
	 */
	SparQLUtils sparQLUtil;

	/**
	 * mongoUtil
	 */
	MongoUtils mongoUtil;
	
	/**
	 * buildSparQLUtil
	 */
	BuildSparQLUtils buildSparQLUtil;

	/**
	 * nlpUtil
	 */
	NLPUtils nlpUtil;

	/**
	 * propertyUtils
	 */
	PropertyUtils propertyUtils;
	/**
	 * restAdapter
	 */
	transient RestAdapter restAdapter;
	/**
	 * restAdapterFuseki
	 */
	transient RestAdapter restAdapterFuseki;

	/**
	 * restAdapterCralwer
	 */
	transient RestAdapter restAdapterCrawler;

	/**
	 * restAdapterUtil
	 */
	transient RestAdapter restAdapterUtil;

	/**
	 * RetrofitBuilder
	 * 
	 * @throws Exception
	 */
	public RetrofitBuilder() {
		init();
	}

	/**
	 * getClassificationMLibUtil
	 * 
	 * @return ClassificationMLibUtil
	 */
	public ClassificationMLibUtils getClassificationMLibUtil() {
		if (restAdapter == null) {
			init();
		}
		return classificationMLibUtil;
	}

	/**
	 * getClassificationMLibUtil
	 * 
	 * @return ClassificationMLibUtil
	 */
	public SparQLUtils getSparQLUtil() {
		if (restAdapterFuseki == null) {
			init();
		}
		return sparQLUtil;
	}

	/**
	 * getNlpUtil
	 * 
	 * @return NLPUtil
	 */
	public NLPUtils getNlpUtil() {
		if (restAdapterUtil == null) {
			init();
		}
		return nlpUtil;
	}
	
	/**
	 * getMongoUtil
	 * 
	 * @return MongoUtil
	 */
	public MongoUtils getMongoUtil() {
		if (restAdapterCrawler == null) {
			init();
		}
		return mongoUtil;
	}

	public void init() {
		try {

			propertyUtils = new PropertyUtils();

			// setting up mlib builder

			OkHttpClient okHttpClient = new OkHttpClient();
			okHttpClient.setConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
			okHttpClient.setReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
			okHttpClient.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
			Builder builder = new RestAdapter.Builder()
					.setEndpoint(new StringBuilder("http://").append(propertyUtils.getClassificationMlibHost())
							.append(":").append(propertyUtils.getClassificationMlibPort()).toString())
					.setClient(new OkClient(okHttpClient));
			builder.setRequestInterceptor(new RequestInterceptor() {
				public void intercept(RequestFacade request) {
					request.addHeader("Content-Type", "application/json");
				}
			});
			restAdapter = builder.build();

			classificationMLibUtil = restAdapter.create(ClassificationMLibUtils.class);

			// setting up NLP Util

			builder = new RestAdapter.Builder()
					.setEndpoint(new StringBuilder("http://").append(propertyUtils.getNLPHost()).append(":")
							.append(propertyUtils.getNLPPort()).toString())
					.setClient(new OkClient(okHttpClient));
			builder.setRequestInterceptor(new RequestInterceptor() {
				public void intercept(RequestFacade request) {
					request.addHeader("Content-Type", "application/json");
				}
			});
			restAdapterUtil = builder.build();

			nlpUtil = restAdapterUtil.create(NLPUtils.class);
			
			

			// setting up Mongo Util

			builder = new RestAdapter.Builder()
					.setEndpoint(new StringBuilder("http://").append(propertyUtils.getCrawlerHost()).append(":")
							.append(propertyUtils.getCrawlerPort()).toString())
					.setClient(new OkClient(okHttpClient));
			builder.setRequestInterceptor(new RequestInterceptor() {
				public void intercept(RequestFacade request) {
					request.addHeader("Content-Type", "application/json");
				}
			});
			restAdapterCrawler= builder.build();

			mongoUtil= restAdapterCrawler.create(MongoUtils.class);
			
			// setting up fuseki server builder
			String credentials = new StringBuilder(propertyUtils.getFusekiUsername()).append(":")
					.append(propertyUtils.getFusekiPassword()).toString();
			// create Base64 encodet string
			final String basic = "Basic " + java.util.Base64.getUrlEncoder().encodeToString(credentials.getBytes());
			builder = new RestAdapter.Builder()
					.setEndpoint(new StringBuilder("http://").append(propertyUtils.getFusekiHost()).append(":")
							.append(propertyUtils.getFusekiPort()).toString())
					.setClient(new OkClient(okHttpClient));
			builder.setRequestInterceptor(new RequestInterceptor() {
				public void intercept(RequestFacade request) {
					request.addHeader("Authorization", basic);
				}
			});
			restAdapterFuseki = builder.build();
			sparQLUtil = restAdapterFuseki.create(SparQLUtils.class);
		} catch (Exception e) {
			System.out.println("#### " + e.getMessage());
		}
	}
}
