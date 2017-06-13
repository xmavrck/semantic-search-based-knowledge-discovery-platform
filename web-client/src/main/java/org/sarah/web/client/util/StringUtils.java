package org.sarah.web.client.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The class StringUtils.
 * @author chandan
 */
public class StringUtils {
    /**
     * static method for pencentEncoding of a string.
     *
     * @param ENC
     * @param input
     * @return String
     */
    public static String percentEncode(String ENC, String input) {
        if (input == null) {
            return "";
        }
        try {
            return URLEncoder.encode(input, ENC)
                    // OAuth encodes some characters differently:
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
            // This could be done faster with more hand-crafted code.
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }

    /**
     * static method for pencentEncoding of a string.
     *
     * @param input
     * @return String
     */
    public static String convertToJson(String input) {
        StringBuilder res = new StringBuilder("{\"");
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '=') {
                res.append("\"").append(":").append("\"");
            } else if (input.charAt(i) == '&') {
                res.append("\"").append(",").append("\"");
            } else {
                res.append(input.charAt(i));
            }
        }
        res.append("\"").append("}");
        return res.toString();
    }
}
