package com.sarah.semantic_analysis.web_oauth_server.validators;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarah.semantic_analysis.web_oauth_server.constants.Constants;
import com.sarah.semantic_analysis.web_oauth_server.dto.MatchCodeDto;
import com.sarah.semantic_analysis.web_oauth_server.service.UserService;

/**
 * The class ResetPasswordValidator.
 * 
 * @author chandan
 */
@Component
public class ResetPasswordValidator implements Constants.ValidatorConstants.ResetPass {

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
	 * validate
	 * 
	 * @param dto
	 * @return Map<String, String>
	 */
	public Map<String, String> validate(MatchCodeDto dto) {
		Map<String, String> errors = new HashMap<String, String>();
		String res = validators.validatePassword(dto.getNewPassword());
		if (res != null) {
			errors.put(PASSWORD, res);
		}
		if (!dto.getConfirmPassword().equals("") && !dto.getNewPassword().equals(dto.getConfirmPassword())) {
			errors.put(CPASSWORD, "Password and Confirm Password should match");
		}
		return errors;
	}
}
