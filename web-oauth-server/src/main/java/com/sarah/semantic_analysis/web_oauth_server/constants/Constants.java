package com.sarah.semantic_analysis.web_oauth_server.constants;

/**
 * The interface Constants.
 * 
 * @author chandan
 */
public interface Constants {
	/**
	 * SEMANTIC_ANALYSIS_OAUTH_CONFIG.
	 * 
	 */
	String SEMANTIC_ANALYSIS_OAUTH_CONFIG = "SEMANTIC_ANALYSIS_OAUTH_CONFIG";
	/**
	 * BASE_PACKAGE_NAME.
	 * 
	 */
	String BASE_PACKAGE_NAME = "com.sarah.semantic_analysis.web_ui";

	/**
	 * The enum DBOperations.
	 * 
	 */
	public enum DBOperations {
		EQUALS, EQUALS_IGNORECASE, IN, DATE_IN, NUMBER_IN, STARTSWITH, ENDSWITH, CONTAINS, ICONTAINS, GT, GTE, LT, LTE, BETWEEN, LOCATION;
	}

	/**
	 * The interface Roles.
	 * 
	 */
	public interface Roles {
		String ROLE_USER = "user";
		String ROLE_EMPLOYEE = "employee";
		String ROLE_ADMIN = "admin";
	}

	/**
	 * The interface OAuthConstants.
	 */
	public interface OAuthConstants {
		// key used in SignatureUtils
		String HMAC_SHA1 = "HmacSHA1";
		// signature method used in OAuthHttp
		String SIGNATURE_METHOD = "HMAC-SHA1";
		// version of oauth being used
		String OAUTH_VERSION = "1.0";
		// protocol used to call authentication api
		String PROTOCOL = "http";
		// buffer size while reading response
		short BUFFER_SIZE = 2048;
		// encoding scheme used to read response and also while generating
		// signature
		String ENC = "UTF-8";
	}

	/**
	 * The interface ValidatorConstants.
	 * 
	 */
	public interface ValidatorConstants {
		/**
		 * The interface SignUp.
		 * 
		 */
		public interface SignUp {
			String FNAME = "signupFirstNameError";
			String LNAME = "signupLastNameError";
			String EMAIL = "signupEmailIdError";
			String PASSWORD = "signupPasswordError";
			String CPASSWORD = "signupConfirmPasswordError";
			String MOBILE_NUMBER = "signupMobileNumberError";
		}

		/**
		 * The interface ResetPass.
		 * 
		 */
		public interface ResetPass {
			String PASSWORD = "resetNewPasswordError";
			String CPASSWORD = "resetConfPasswordError";
		}
	}

	/**
	 * The interface Controllers.
	 */
	public interface Controllers {
		String BASE_URL = "/oauth";
		String FREE_TRIAL = BASE_URL + "/free-trial";
		String SIGNUP = BASE_URL + "/signup";
		String SIGNUP_EMPLOYEE = BASE_URL + "/signup-employee";
		String VALIDATE_MOBILE = "/check-mobile";
		String VALIDATE_EMAIL = "/check-email";
		String GETTING_STARTED = "/get-started";
		String VERIFY = BASE_URL + "/verify";
		String ACCOUNT_ACTIVATION = BASE_URL + "/accountactivation";
		String EMPLOYEE_ACCOUNT_ACTIVATION = BASE_URL + "/account-activation-employee";
		String RESET = BASE_URL + "/reset";
		String FORGET_PASSWORD = BASE_URL + "/forgetpassword";
		String PASSWORD_CHANGED_SUCCESS = BASE_URL + "/password-changed-success";
		String FORGOT_EMAIL_SUCCESS = BASE_URL + "/forgot-email-success";
		String LINK_EXPIRED = BASE_URL + "/linkexpired";
		String PASSWORD_SUCCESS = BASE_URL + "/password-success";
		String ACTIVATION = BASE_URL + "/activation";
		String ACCOUNT_ACTIVATION_CODE = BASE_URL + "/account-activation";

		String RESEND = BASE_URL + "/resendlink";

		String APPS = BASE_URL + "/apps";
		String EDIT_APP = BASE_URL + "/edit-app";

		String REGISTER_APP = BASE_URL + "/register";
		String DELETE_APP = BASE_URL + "/delete-app";

		String REQUEST_TOKEN = BASE_URL + "/request_token";
		String ACCESS_TOKEN = BASE_URL + "/access_token";
		String CHECK_ACCESS_TOKEN = BASE_URL + "/check_access_token";
		String AUTHENTICATE = BASE_URL + "/authenticate";
		String ALLOW_ACCESS = BASE_URL + "/allowaccess";
		String DISALLOW_ACCESS = BASE_URL + "/disallowaccess";

		String HOME = "home";
	}

	/**
	 * ApiConstants
	 */
	public interface ApiConstants {
		String CHECK_ACCESS_TOKEN_PATH = "check_access_token";
		String ACCESS_TOKEN_PATH = "access_token";
		String REQUEST_TOKEN_PATH = "request_token";
		String AUTHENTICATE_PATH = "authenticate";
		String CALLBACK_URL = "/home";
	}
}
