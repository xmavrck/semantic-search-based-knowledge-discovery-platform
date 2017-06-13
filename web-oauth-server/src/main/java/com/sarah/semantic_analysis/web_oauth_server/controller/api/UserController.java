package com.sarah.semantic_analysis.web_oauth_server.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sarah.semantic_analysis.web_oauth_server.dto.SearchUserDto;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.repositories.UserRepo;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.CodeUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EmailUtils;
import com.sarah.semantic_analysis.web_oauth_server.utils.EncryptionUtils;

/**
 * The class UserController.
 * 
 * @author chandan
 */
@RepositoryRestController
public class UserController {
	/**
	 * userService
	 */
	@Autowired
	private UserService userService;
	/**
	 * userRepo
	 */
	@Autowired
	private UserRepo userRepo;
	/**
	 * emailUtils
	 */
	@Autowired
	private EmailUtils emailUtils;
	/**
	 * codeUtils
	 */
	@Autowired
	private CodeUtils codeUtils;
	/**
	 * encryptionUtils
	 */
	@Autowired
	private EncryptionUtils encryptionUtils;

	/**
	 * search
	 * 
	 * @param dto
	 * @param pageable
	 * @return ResponseEntity<Page<User>>
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	public @ResponseBody ResponseEntity<Page<User>> search(@ModelAttribute SearchUserDto dto, Pageable pageable)
			throws Exception {
		Page<User> list = userService.search(dto, pageable);
		return new ResponseEntity<Page<User>>(list, HttpStatus.OK);
	}

	/**
	 * put
	 * 
	 * @param id
	 * @param user
	 * @return ResponseEntity<User>
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "/users/{id}")
	public @ResponseBody ResponseEntity<User> put(@PathVariable("id") String id, @RequestBody User user) {
		try {
			User userDB = userRepo.findOne(id);
			if (userDB.getRole().equals("employee") && user.getIsEnabled() != null) {
				userDB.setIsEnabled(user.getIsEnabled());
				if (user.getIsEnabled()) {
					final String randomCode = codeUtils.generateRandomCode();
					userDB.setPassword(encryptionUtils.encrpt(randomCode));
					emailUtils.sendEmployeeAccountCreation(userDB.getFirstName(), userDB.getLastName(),
							userDB.getEmailId(), randomCode);
				} else {
					emailUtils.sendEmployeeAccountDeActivation(userDB.getFirstName(), userDB.getLastName(),
							userDB.getEmailId());
				}
			}
			patch(userDB, user);
			userDB = userRepo.save(userDB);
			return new ResponseEntity<User>(userDB, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}

	/**
	 * search
	 * 
	 * @param id
	 * @return ResponseEntity<User>
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
	public @ResponseBody ResponseEntity<User> search(@PathVariable String id) throws Exception {
		try {
			User user = userRepo.findOne(id);
			if (user != null) {
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
	}

	/**
	 * patch
	 * 
	 * @param userDB
	 * @param user
	 * @return User
	 * @throws Exception
	 */
	User patch(User userDB, User user) throws Exception {
		if (user.getIsEnabled() != null) {
			userDB.setIsEnabled(user.getIsEnabled());
		}
		if (user.getEmailId() != null) {
			userDB.setEmailId(user.getEmailId());
		}
		if (user.getFirstName() != null) {
			userDB.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			userDB.setLastName(user.getLastName());
		}
		if (user.getMobileNumber() != null) {
			userDB.setMobileNumber(user.getMobileNumber());
		}
		if (user.getPassword() != null) {
			userDB.setPassword(encryptionUtils.encrpt(user.getPassword()));
		}
		return userDB;
	}

}