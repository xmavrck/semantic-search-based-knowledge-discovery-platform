package com.sarah.semantic_analysis.web_oauth_server.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sarah.semantic_analysis.web_oauth_server.utils.ConfigUtils;

/**
 * The class Validators.
 * 
 * @author chandan
 */
@Component
public class Validators {
	/**
	 * EMAIL
	 */
	private static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	/**
	 * NUMERIC
	 */
	private static final String NUMERIC = "[0-9]+";
	/**
	 * PASSWORD
	 */
	private static final String PASSWORD = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	/**
	 * ALPHABETS
	 */
	private static final String ALPHABETS = "[a-zA-Z]+\\.?";

	/**
	 * emailPattern
	 */
	private Pattern emailPattern;
	/**
	 * numberPattern
	 */
	private Pattern numberPattern;
	/**
	 * passwordPattern
	 */
	private Pattern passwordPattern;
	/**
	 * alphaPattern
	 */
	private Pattern alphaPattern;
	/**
	 * matcher
	 */
	private Matcher matcher;

	/**
	 * configUtils
	 */
	@Autowired
	ConfigUtils configUtils;

	/**
	 * Validators
	 */
	public Validators() {
		emailPattern = Pattern.compile(EMAIL);
		numberPattern = Pattern.compile(NUMERIC);
		passwordPattern = Pattern.compile(PASSWORD);
		alphaPattern = Pattern.compile(ALPHABETS);
	}

	/**
	 * validateEmail
	 * 
	 * @param email
	 * @return String
	 */
	public String validateEmail(final String email) {
		if (email == null || email.trim().equals("")) {
			return configUtils.getErrorMessageEmptyField();
		}
		matcher = emailPattern.matcher(email);
		if (!matcher.matches()) {
			return configUtils.getErrorMessageEmailValid();
		}
		return null;
	}

	/**
	 * validatePhone
	 * 
	 * @param phoneNo
	 * @return String
	 */
	public String validatePhone(final String phoneNo) {
		if (phoneNo == null || phoneNo.trim().equals("")) {
			return configUtils.getErrorMessageEmptyField();
		}
		matcher = numberPattern.matcher(phoneNo);
		if (!matcher.matches()) {
			return configUtils.getErrorMessageMobileNumberValid();
		}
		if (phoneNo.length() > 15 || phoneNo.length() < 10) {
			return configUtils.getErrorMessageMobileNumberLength();
		}
		return null;
	}

	/**
	 * validateAlphabetic
	 * 
	 * @param name
	 * @return String
	 */
	public String validateAlphabetic(final String name) {
		if (name == null || name.trim().equals(""))
			return configUtils.getErrorMessageEmptyField();
		matcher = alphaPattern.matcher(name);
		if (!matcher.matches()) {
			return configUtils.getErrorMessageAlphabetic();
		}
		return null;
	}

	/**
	 * validatePassword
	 * 
	 * @param password
	 * @return String
	 */
	public String validatePassword(final String password) {
		if (password == null || password.trim().equals(""))
			return configUtils.getErrorMessageEmptyField();
		matcher = passwordPattern.matcher(password);
		if (!matcher.matches()) {
			return configUtils.getErrorMessagePasswordError();
		}
		return null;
	}
}
