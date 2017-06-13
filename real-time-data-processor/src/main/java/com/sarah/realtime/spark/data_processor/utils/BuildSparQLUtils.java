package com.sarah.realtime.spark.data_processor.utils;

import com.sarah.realtime.spark.data_processor.dto.BuildSparQLRequest;
import com.sarah.realtime.spark.data_processor.dto.BuildSparQLResponse;

import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * The interface BuildSparQLApi.
 * 
 * @author chandan
 */
public interface BuildSparQLUtils {
	/**
	 * getWords
	 * 
	 * @param buildSparQLRequest
	 * @param response
	 * @return BuildSparQLResponse
	 * @throws RetrofitError
	 */
	@POST("/query")
	BuildSparQLResponse getWords(@Body BuildSparQLRequest buildSparQLRequest) throws RetrofitError;

}
