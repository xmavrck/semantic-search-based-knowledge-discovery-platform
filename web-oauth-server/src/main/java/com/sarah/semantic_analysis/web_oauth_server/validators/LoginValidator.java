package com.sarah.semantic_analysis.web_oauth_server.validators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.entities.User;

/**
 * The class LoginValidator.
 * 
 * @author chandan
 */
@Component
public class LoginValidator implements Constants.ValidatorConstants.SignUp {
	/**
	 * validators
	 */
	@Autowired
	Validators validators;

	/**
	 * validate
	 * 
	 * @param userMaster
	 * @param cpassword
	 * @return Map<String, String>
	 */
	public Map<String, String> validate(User userMaster, String cpassword) {
		Map<String, String> errors = new HashMap<String, String>();
		String res = validators.validateEmail(userMaster.getEmailId());
		if (res != null) {
			errors.put(EMAIL, res);
		}
		return errors;
	}

}
