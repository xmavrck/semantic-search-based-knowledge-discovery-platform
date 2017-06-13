package com.sarah.semantic_analysis.web_oauth_server.validators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;
import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;

/**
 * The class SignUpValidator.
 * 
 * @author chandan
 */
@Component
public class SignUpValidator implements Constants.ValidatorConstants.SignUp {
	/**
	 * validators
	 */
	@Autowired
	Validators validators;

	/**
	 * userService
	 */
	@Autowired
	UserService userService;

	/**
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;

	/**
	 * validate
	 * 
	 * @param userMaster
	 * @param cpassword
	 * @param isPassword
	 * @return Map<String, String>
	 */
	public Map<String, String> validate(User userMaster, String cpassword, boolean isPassword) {
		Map<String, String> errors = new HashMap<String, String>();
		String res = validators.validateEmail(userMaster.getEmailId());
		if (res != null) {
			errors.put(EMAIL, res);
		} else if (userService.findByEmail(userMaster.getEmailId()) != null) {
			errors.put(EMAIL, configUtils.getErrorMessageEmailAlreadyregistered());
		}
		res = validators.validateAlphabetic(userMaster.getFirstName());
		if (res != null) {
			errors.put(FNAME, res);
		}
		res = validators.validateAlphabetic(userMaster.getLastName());
		if (res != null) {
			errors.put(LNAME, res);
		}
		res = validators.validatePhone(userMaster.getMobileNumber());
		if (res != null) {
			errors.put(MOBILE_NUMBER, res);
		} else if (userService.findByMobileNumber(userMaster.getMobileNumber()) != null) {
			errors.put(MOBILE_NUMBER, configUtils.getErrorMessageMobileAlreadyregistered());
		}
		if (isPassword) {
			res = validators.validatePassword(userMaster.getPassword());
			if (res != null) {
				errors.put(PASSWORD, res);
			}
			if (!cpassword.equals("") && !userMaster.getPassword().equals(cpassword)) {
				errors.put(CPASSWORD, configUtils.getErrorMessagePasswordConfirmPasswordNotMatched());
			}
		}
		return errors;
	}
}
