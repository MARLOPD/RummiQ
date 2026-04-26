package com.rummyq.backend.config;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptEncryption {

    public static String Encrypt(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt(12));
    }

    public static boolean Verify(String plainText, String passwordHash) {
        return BCrypt.checkpw(plainText, passwordHash);
    }
}