package org.sarah.web.client.controller.api;

import org.sarah.web.client.dto.Response;
import org.sarah.web.client.dto.SearchUserDto;
import org.sarah.web.client.dto.UserPage;
import org.sarah.web.client.entities.User;
import org.sarah.web.client.util.EncryptionUtils;
import org.sarah.web.client.util.RetrofitBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The class ManageEmployeeRestController.
 * 
 * @author chandan
 */
@Controller
@RequestMapping("/api/users")
public class ManageEmployeeRestController {

	/**
	 * retrofitBuilder
	 */
	@Autowired
	RetrofitBuilder retrofitBuilder;
	
	/**
	 * encryptionUtils
	 */
	@Autowired
	EncryptionUtils encryptionUtils;

	/**
	 * employees
	 * 
	 * @param searchUserDto
	 * @param pageable
	 * @return ResponseEntity<UserPage>
	 */
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<UserPage> employees(SearchUserDto searchUserDto, Pageable pageable) {
		try {
			return new ResponseEntity<UserPage>(retrofitBuilder.getManageEmployeesApi().get(searchUserDto.getRole()),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<UserPage>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * patch
	 * 
	 * @param id
	 * @param user
	 * @return ResponseEntity<Response>
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public @ResponseBody ResponseEntity<Response> patch(@PathVariable String id, @RequestBody User user) {
		try {
			if (user.getPassword() != null) {
				User userApi = retrofitBuilder.getManageEmployeesApi().getById(id);
				if (userApi == null) {
					return new ResponseEntity<Response>(new Response(400, "Your Old Password doesn't match"),
							HttpStatus.BAD_REQUEST);
				} else {
					if (!userApi.getPassword().equals(encryptionUtils.encrpt(user.getOldPassword()))) {
						return new ResponseEntity<Response>(new Response(400, "Your Old Password doesn't match"),
								HttpStatus.BAD_REQUEST);
					}
				}
			}
			User userResponse = retrofitBuilder.getManageEmployeesApi().patch(id, user);
			System.out.println("userResponse" + userResponse);
			if (userResponse != null) {
				return new ResponseEntity<Response>(new Response(200), HttpStatus.OK);
			} else {
				return new ResponseEntity<Response>(new Response(400, "Bad Request"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Response>(new Response(400, "Bad Request"), HttpStatus.BAD_REQUEST);
	}
}