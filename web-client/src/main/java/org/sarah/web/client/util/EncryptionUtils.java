package org.sarah.web.client.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

/**
 * The class EncryptionUtils.
 * 
 * @author chandan
 */
@Component
public class EncryptionUtils {
	/**
	 * key
	 */
	static String key = "BAcKYaRd1237RyaB";
	/**
	 * TOKEN_WORDS
	 */
	static final String TOKEN_WORDS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	/**
	 * rnd
	 */
	static SecureRandom rnd = new SecureRandom();
	/**
	 * TOKEN_LENGTH
	 */
	static final int TOKEN_LENGTH = 50;

	/**
	 * encrpt
	 * 
	 * @param text
	 * @return String
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String encrpt(String text) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] encrypted = cipher.doFinal(text.getBytes());
		return new String(encrypted);
	}

	/**
	 * decrypt
	 * 
	 * @param text
	 * @return String
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String decrypt(String text) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		byte[] deCrypted = cipher.doFinal(text.getBytes());
		return new String(deCrypted);
	}

	/**
	 * getToken
	 * 
	 * @return String
	 */
	public String getToken() {
		StringBuilder sb = new StringBuilder(TOKEN_LENGTH);
		for (int i = 0; i < TOKEN_LENGTH; i++)
			sb.append(TOKEN_WORDS.charAt(rnd.nextInt(TOKEN_WORDS.length())));
		return sb.toString();
	}
}
