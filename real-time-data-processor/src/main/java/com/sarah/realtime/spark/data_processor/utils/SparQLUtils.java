package com.sarah.realtime.spark.data_processor.utils;

import retrofit.RetrofitError;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * The interface SparQLApi.
 * 
 * @author chandan
 */
public interface SparQLUtils {
	/**
	 * saveRDF
	 * 
	 * @param datasetName
	 * @param file
	 * @return Object
	 * @throws RetrofitError
	 */
	@Multipart
	@POST("/{datasetName}/upload")
	Object saveRDF(@Path("datasetName") String datasetName, @Part("file") TypedFile file) throws RetrofitError;

}
