package com.example.medicineApp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Sabina on 2014-12-05.
 */
public class Password {

    public static String HashPassword(CharSequence pass) {
          try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.toString().getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
