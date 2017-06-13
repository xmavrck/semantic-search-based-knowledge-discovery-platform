package com.sarah.semantic_analysis.web_oauth_server.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;
/**
 * The class CodeUtils.
 * 
 * @author chandan
 */
@Component
public class CodeUtils {
	/**
	 * generateSixDigitCode
	 * 
	 * @return Integer
	 */	
	public Integer generateSixDigitCode() {
		Random ran = new Random();
		int code = (100000 + ran.nextInt(900000));
		return code;
	}
	/**
	 * validateAccessToken
	 */
	private SecureRandom random = new SecureRandom();

	/**
	 * generateRandomCode
	 * 
	 * @return String
	 */	
	public String generateRandomCode() {
		return new BigInteger(130, random).toString(32);
	}

}
