package com.sarah.web_crawler.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarah.web_crawler.retrofit.BuildSparQLApi;
import com.sarah.web_crawler.retrofit.SparQLApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;

@Component
public class RetrofitBuilder {

	/**
	 * restAdapter
	 */
	private RestAdapter restAdapter;

	/**
	 * restAdapterPython
	 */
	private RestAdapter restAdapterPython;

	/**
	 * CONNECT_TIME_OUT,READ_TIME_OUT,WRITE_TIME_OUT
	 */
	private static final long CONNECT_TIME_OUT = 60, READ_TIME_OUT = 60, WRITE_TIME_OUT = 60;
	/**
	 * sparQLApi
	 */
	SparQLApi sparQLApi;

	/**
	 * buildSparQLApi
	 */
	BuildSparQLApi buildSparQLApi;

	/**
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;

	@PostConstruct
	public void initialize() {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
		okHttpClient.setReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
		okHttpClient.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
		Builder builder = new RestAdapter.Builder()
				.setEndpoint(new StringBuilder("http://").append(configUtils.getFusekiHost()).append(":")
						.append(configUtils.getFusekiPort()).toString())
				.setClient(new OkClient(okHttpClient));

		builder.setRequestInterceptor(new RequestInterceptor() {
			@Override
			public void intercept(RequestFacade request) {
				// request.addHeader("Content-Type",
				// "application/sparql-results+json; charset=utf-8");
				request.addHeader("Accept", "application/sparql-results+json; charset=utf-8");
				request.addHeader("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(
						(configUtils.getFusekiHost() + ":" + configUtils.getFusekiPassword()).getBytes()));
			}
		});
		restAdapter = builder.build();
		sparQLApi = restAdapter.create(SparQLApi.class);

		Builder builderPython = new RestAdapter.Builder()
				.setEndpoint(new StringBuilder("http://").append(configUtils.getPythonHost()).append(":")
						.append(configUtils.getPythonPort()).toString())
				.setClient(new OkClient(okHttpClient));

		builderPython.setRequestInterceptor(new RequestInterceptor() {
			@Override
			public void intercept(RequestFacade request) {
				request.addHeader("Content-Type", "application/json");
			}
		});
		restAdapterPython = builderPython.build();
		buildSparQLApi = restAdapterPython.create(BuildSparQLApi.class);

	}

	public SparQLApi getSparQLApi() {
		return sparQLApi;
	}

	public BuildSparQLApi getBuildSparQLApi() {
		return buildSparQLApi;
	}
}
