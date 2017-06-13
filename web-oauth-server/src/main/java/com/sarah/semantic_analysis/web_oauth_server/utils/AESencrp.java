package com.sarah.semantic_analysis.web_oauth_server.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import org.apache.commons.codec.binary.Base64;
/**
 * The class AESencrp.
 * 
 * @author chandan
 */
@Component
public class AESencrp {
	/**
	 * ALGO
	 */
    private static final String ALGO = "AES";
    /**
	 * keyValue
	 */
    private static final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
                    'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    /**
	 * encrypt
	 * 
	 * @param data
	 * @return String
	 * @throws Exception
	 */	
    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = new Base64().encodeToString(encVal);
        return encryptedValue;
    }
    /**
	 * decrypt
	 * 
	 * @param encryptedData
	 * @return String
	 * @throws Exception
	 */	
    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new Base64().decode(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    /**
	 * generateKey
	 * 
	 * @return Key
	 * @throws Exception
	 */	
    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

}


