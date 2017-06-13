package com.sarah.realtime.spark.data_processor.utils;

import com.sarah.realtime.spark.data_processor.dto.NamedEntityExtractionRequest;
import com.sarah.realtime.spark.data_processor.dto.NamedEntityExtractionResponse;

import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * The interface NLPApi.
 * 
 * @author chandan
 */
public interface NLPUtils {
	/**
	 * getNames
	 * 
	 * @param namedEntityExtractionRequest
	 * @return NamedEntityExtractionResponse
	 * @throws RetrofitError
	 */
	@POST("/nlp/names")
	NamedEntityExtractionResponse getNames(@Body NamedEntityExtractionRequest namedEntityExtractionRequest)
			throws RetrofitError;

}
