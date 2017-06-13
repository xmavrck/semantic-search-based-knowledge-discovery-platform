package org.sarah.web.client.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.sarah.web.client.retrofit.ManageEmployeesApi;
import org.sarah.web.client.retrofit.StanbolApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class RetrofitBuilder {
	/**
	 * restAdapter
	 */
	private RestAdapter restAdapter;

	/**
	 * CONNECT_TIME_OUT,READ_TIME_OUT,WRITE_TIME_OUT
	 */
	private static final long CONNECT_TIME_OUT = 60, READ_TIME_OUT = 60, WRITE_TIME_OUT = 60;
	/**
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;

	/**
	 * manageEmployeesApi
	 */
	ManageEmployeesApi manageEmployeesApi;

	/**
	 * initialize
	 * 
	 */
	@PostConstruct
	public void initialize() {
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
		okHttpClient.setReadTimeout(READ_TIME_OUT, TimeUnit.SECONDS);
		okHttpClient.setWriteTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS);
		Builder builder = new RestAdapter.Builder().setEndpoint(configUtils.getoAuthRestBaseUrl())
				.setClient(new OkClient(okHttpClient)).setLogLevel(RestAdapter.LogLevel.FULL);
		builder.setRequestInterceptor(new RequestInterceptor() {
			public void intercept(RequestFacade request) {
				request.addHeader("Content-Type", "application/json");
			}
		});
		restAdapter = builder.build();

	}

	/**
	 * getManageEmployeesApi
	 * 
	 * @return ManageEmployeesApi
	 */
	public ManageEmployeesApi getManageEmployeesApi() {
		return manageEmployeesApi;
	}

	/**
	 * getRestAdapter
	 * 
	 * @return RestAdapter
	 */
	public RestAdapter getRestAdapter() {
		return restAdapter;
	}
}
