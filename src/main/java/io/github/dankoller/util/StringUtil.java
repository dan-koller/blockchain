package io.github.dankoller.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class StringUtil {

    /**
     * Applies SHA-256 to a string and returns the result.
     *
     * @param input The string to be hashed
     * @return The hashed string
     */
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
