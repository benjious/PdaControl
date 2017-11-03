package com.benjious.pdacontrol.util;

import java.security.Security;
import java.security.MessageDigest;

/**
 * Created by Benjious on 2017/11/3.
 */

public class MD5Util {
    private final static String[] strDigits={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    public static String createMD5Hash(String str) {
        String result ;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = messageDigest.digest(str.getBytes());
            result = byteToString(md5Bytes);
        } catch (Exception e) {
            return "";
        }
        return result.toUpperCase();
    }

    private static String byteToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            builder.append(byteToArrayString(bytes[i]));
        }
        return builder.toString();
    }

    private static String byteToArrayString(byte b) {
        int bi = b;
        if (bi < 0) {
            bi += 256;
        }
        int bi1 = bi / 16;
        int bi2 = bi % 16;
        return strDigits[bi1] + strDigits[bi2];
    }
}
