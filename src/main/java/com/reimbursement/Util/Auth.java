package com.reimbursement.Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;

public class Auth {
    public static String hashPassword(String pass) {
        String hashedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] salt = getSalt();
            md.update(salt);
            byte[] hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            for (byte b : salt) {
                sb2.append(String.format("%02x", b));
            }
            hashedPassword = sb2.toString() + sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    public static boolean checkPassword(String pass, String hashedPassword) {
        String saltHex = hashedPassword.substring(0, 32);
        byte[] salt = hexToByteArray(saltHex);
        boolean isSame = false;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            if (hashedPassword.equals(saltHex + sb.toString())) {
                isSame = true;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return isSame;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom  random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    } 

    private static byte[] hexToByteArray(String hex) {
        byte[] salt = new byte[16];
        for (int i = 0; i < salt.length; i++) {
            int index = i * 2;
            salt[i] = (byte) Integer.parseInt(hex.substring(index, index + 2), 16);
        }
        return salt;
    }


}
