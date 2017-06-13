package com.sarah.realtime.spark.data_processor.utils;

import com.sarah.realtime.spark.data_processor.dto.ClassificationMlibRequest;
import com.sarah.realtime.spark.data_processor.dto.ClassificationMlibResponse;

import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * The interface ClassificationMLibApi.
 * 
 * @author chandan
 */
public interface ClassificationMLibUtils {
	/**
	 * getClassificationResults
	 * 
	 * @param classificationMlibRequest
	 * @return ClassificationMlibResponse
	 * @throws RetrofitError
	 */
	@POST("/")
	ClassificationMlibResponse getClassificationResults(@Body ClassificationMlibRequest classificationMlibRequest)
			throws RetrofitError;

}
