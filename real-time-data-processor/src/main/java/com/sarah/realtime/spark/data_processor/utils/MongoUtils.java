package com.sarah.realtime.spark.data_processor.utils;

import com.sarah.realtime.spark.data_processor.dto.UrlHistory;

import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * The interface MongoUtils.
 * 
 * @author chandan
 */
public interface MongoUtils {
	/**
	 * update
	 * 
	 * @param id
	 * @param url
	 * @return UrlHistory
	 * @throws RetrofitError
	 */
	@PUT("/url-history/{id}")
	UrlHistory update(@Path("id") String id, @Body UrlHistory url)
			throws RetrofitError;

}
