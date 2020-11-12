package com.nagarro.account.utils;

import java.security.MessageDigest;

public class HashingUtil {
	
	public static String encryptString(String msg) {
        MessageDigest md;
        String out = "";
        try {
            md = MessageDigest.getInstance("SHA-512");
            
            md.update(msg.getBytes());
            byte[] mb = md.digest();
            
            for (byte temp : mb) {
                String s = Integer.toHexString(temp);
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }
            
        } catch (Exception e) {
            System.out.print("ERROR No algo found: {}"+ e);
        }
        return out;
    }

}
