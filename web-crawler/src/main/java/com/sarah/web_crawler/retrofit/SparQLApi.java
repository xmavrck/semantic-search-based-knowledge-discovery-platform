package com.sarah.web_crawler.retrofit;

import com.sarah.web_crawler.dto.SparQLResult;

import retrofit.RetrofitError;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * The interface SparQLApi.
 * 
 * @author chandan
 */
public interface SparQLApi {
	/**
	 * query
	 * 
	 * @param datasetName
	 * @param query
	 * @return Object
	 * @throws RetrofitError
	 */
	@POST("/{datasetName}/query")
	@FormUrlEncoded
	SparQLResult query(@Path("datasetName") String datasetName, @Field("query") String query) throws RetrofitError;
}
