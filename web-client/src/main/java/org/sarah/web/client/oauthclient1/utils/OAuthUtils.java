package org.sarah.web.client.oauthclient1.utils;

/**
 * The class OAuthUtils.
 * @author chandan
 */
public class OAuthUtils {

    /*
    *static method to get current timestamp
    *@return String
     */
    public static String getTimeStamp() {
        return "" + (System.currentTimeMillis() / 1000);
    }

    /*
    *static method to get oauth-nounce
    *@return String
     */
    public static String getOAuthNounce() {
        return "" + (int) (Math.random() * 100000000);
    }
}
