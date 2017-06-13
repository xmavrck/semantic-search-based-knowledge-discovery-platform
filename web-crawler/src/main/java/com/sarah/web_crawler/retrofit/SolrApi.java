//package com.sarah.web_crawler.retrofit;
//
//import com.sarah.web_crawler.dto.WebScrappyDataDto;
//
//import retrofit.RetrofitError;
//import retrofit.http.Body;
//import retrofit.http.POST;
//import retrofit.http.Path;
//
///**
// * The interface SolrApi
// * @author chandan
// */
//public interface SolrApi {
//	/**
//	 * save
//	 * 
//	 * @param collectionName
//	 * @param webScrappyDataDto
//	 * @throws RetrofitError
//	 */
//	@POST("/solr/{collection-name}/update/")
//	Void save(@Path("collection-name") String collectionName, @Body WebScrappyDataDto[] webScrappyDataDto)
//			throws RetrofitError;
//
//}
