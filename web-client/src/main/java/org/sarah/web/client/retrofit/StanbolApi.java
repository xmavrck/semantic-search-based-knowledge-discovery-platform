package org.sarah.web.client.retrofit;

import java.util.Map;

import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * The class StanbolApi.
 * 
 * @author chandan
 */
public interface StanbolApi {
	
	/**
	 * search
	 * @param queryTerm
	 * @return Map
	 * @throws RetrofitError
	 */
	@GET("/contenthub/contenthub/search/featured")
	Map search(@Query("queryTerm") String queryTerm) throws RetrofitError;

}
