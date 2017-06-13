package org.sarah.web.client.retrofit;

import org.sarah.web.client.dto.UserPage;
import org.sarah.web.client.entities.User;

import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * The interface ManageEmployeesApi.
 * 
 * @author chandan
 */
public interface ManageEmployeesApi {
	/**
	 * save
	 * 
	 * @param user
	 * @return User
	 * @throws RetrofitError
	 */
	@POST("/users")
	User save(@Body User user) throws RetrofitError;

	/**
	 * delete
	 * 
	 * @param id
	 * @throws RetrofitError
	 */
	@DELETE("/users/{id}")
	Void delete(@Path("id") String id) throws RetrofitError;

	/**
	 * patch
	 * 
	 * @param id
	 * @param user
	 * @return User
	 * @throws RetrofitError
	 */
	@PATCH("/users/{id}")
	User patch(@Path("id") String id, @Body User user) throws RetrofitError;

	/**
	 * get
	 * 
	 * @param role
	 * @return UserPage
	 * @throws RetrofitError
	 */
	@GET("/users")
	UserPage get(@Query("role") String role) throws RetrofitError;

	/**
	 * getById
	 * 
	 * @param id
	 * @return User
	 * @throws RetrofitError
	 */
	@GET("/users/{id}")
	User getById(@Path("id") String id) throws RetrofitError;

}
