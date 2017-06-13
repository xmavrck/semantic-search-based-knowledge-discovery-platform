package com.sarah.web_crawler.retrofit;

import java.util.Map;

import com.sarah.web_crawler.dto.BuildSparQLRequest;
import com.sarah.web_crawler.dto.BuildSparQLResponse;
import com.sarah.web_crawler.dto.RankingDto;

import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * The interface BuildSparQLApi.
 * 
 * @author chandan
 */
public interface BuildSparQLApi {
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

	/**
	 * getResearchAreafromProjectDescription
	 * 
	 * @param buildSparQLRequest
	 * @param response
	 * @return BuildSparQLResponse
	 * @throws RetrofitError
	 */
	@POST("/description")
	Map<String, Object> getResearchAreafromProjectDescription(@Body BuildSparQLRequest buildSparQLRequest)
			throws RetrofitError;

	/**
	 * getRanking
	 * 
	 * @param rankingDto
	 * @return RankingDto
	 * @throws RetrofitError
	 */
	@POST("/ranking")
	RankingDto getRanking(@Body RankingDto rankingDto) throws RetrofitError;

}
