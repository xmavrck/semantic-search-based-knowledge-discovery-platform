package com.sarah.semantic_analysis.web_oauth_server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * The class ConfigUtils.
 * 
 * @author chandan
 */
@Component
public class ConfigUtils {

	@Value("${mongo.database}")
	private String mongoDatabaseName;

	@Value("${mongo.hostname}")
	private String mongoHostName;

	@Value("${mongo.port}")
	private Integer mongoPortNo;

	public String getMongoDatabaseName() {
		return mongoDatabaseName;
	}

	public String getMongoHostName() {
		return mongoHostName;
	}

	public Integer getMongoPortNo() {
		return mongoPortNo;
	}
	
	/*------------------------------------------------------------------*/
	/* EnvironmentVariables for Sign up Page */
	@Value("${errorMessage.emailAlreadyregistered}")
	private String errorMessageEmailAlreadyregistered;

	@Value("${errorMessage.mobileAlreadyregistered}")
	private String errorMessageMobileAlreadyregistered;

	@Value("${errorMessage.passwordConfirmPasswordNotMatched}")
	private String errorMessagePasswordConfirmPasswordNotMatched;

	@Value("${errorMessage.emptyField}")
	private String errorMessageEmptyField;

	@Value("${errorMessage.passwordError}")
	private String errorMessagePasswordError;

	@Value("${errorMessage.alphabetic}")
	private String errorMessageAlphabetic;

	@Value("${errorMessage.mobileNumberLength}")
	private String errorMessageMobileNumberLength;

	@Value("${errorMessage.mobileNumberValid}")
	private String errorMessageMobileNumberValid;

	@Value("${errorMessage.emailValid}")
	private String errorMessageEmailValid;

	public String getErrorMessagePasswordError() {
		return errorMessagePasswordError;
	}

	public String getErrorMessageAlphabetic() {
		return errorMessageAlphabetic;
	}

	public String getErrorMessageMobileNumberLength() {
		return errorMessageMobileNumberLength;
	}

	public String getErrorMessageMobileNumberValid() {
		return errorMessageMobileNumberValid;
	}

	public String getErrorMessageEmailValid() {
		return errorMessageEmailValid;
	}

	public String getErrorMessageEmptyField() {
		return errorMessageEmptyField;
	}

	public String getErrorMessagePasswordConfirmPasswordNotMatched() {
		return errorMessagePasswordConfirmPasswordNotMatched;
	}

	public String getErrorMessageMobileAlreadyregistered() {
		return errorMessageMobileAlreadyregistered;
	}

	public String getErrorMessageEmailAlreadyregistered() {
		return errorMessageEmailAlreadyregistered;
	}
	/*------------------------------------------------------------------*/

	/*
	 * Firstly add all the below values in environment variables and also take
	 * the properties file from chandan veer
	 */

	@Value("${semanticanalysis.oauth.company}")
	private String company;

	public String getCompany() {
		return company;
	}

	@Value("${semanticanalysis.oauth.hostname}")
	private String modelfactoryOauthHostname;

	public String getSemanticAnalysisOauthHostname() {
		return modelfactoryOauthHostname;
	}

	@Value("${semanticanalysis.oauth.baseUrl}")
	private String modelFactoryOAuthBaseUrl;

	public String getSemanticAnalysisOAuthBaseUrl() {
		return modelFactoryOAuthBaseUrl;
	}

	public String getAccountActivationLink() {
		return new StringBuilder(
				getSemanticAnalysisOauthHostname() + "/" + getSemanticAnalysisOAuthBaseUrl() + "/accountactivation?key=")
						.toString();
	}

	public String getResetLink() {
		return new StringBuilder(getSemanticAnalysisOauthHostname() + "/" + getSemanticAnalysisOAuthBaseUrl() + "/reset?sstt=")
				.toString();
	}

	@Value("${semanticanalysis.oauth.emailId}")
	private String modelfactoryOauthEmailId;

	public String getSemanticAnalysisOauthEmailId() {
		return modelfactoryOauthEmailId;
	}

	public String getForgotPasswordLink() {
		return new StringBuilder(getSemanticAnalysisOauthHostname() + "/" + getSemanticAnalysisOAuthBaseUrl() + "/verify?key=")
				.toString();
	}
	
	@Value("${semanticanalysis.oauth.accountActivationLinkExpiry}")
	private String accountActivationLinkExpiry;

	public String getAccountActivationLinkExpiry() {
		return accountActivationLinkExpiry;
	}

	@Value("${semanticanalysis.hostname}")
	private String modelfactoryHostname;

	public String getSemanticAnalysisHostname() {
		return modelfactoryHostname;
	}

	@Value("${semanticanalysis.oauth.accountRecoveryLinkExpiry}")
	private String accountRecoveryLinkExpiry;

	public String getAccountRecoveryLinkExpiry() {
		return accountRecoveryLinkExpiry;
	}
}
