package com.rummyq.util;

import java.security.MessageDigest;

/**
 * Utilidad para hashear texto con SHA-256.
 * Nunca se envían contraseñas en texto plano por la red.
 */
public class HashUtil {

    public static String sha256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error al hashear: " + e.getMessage());
        }
    }
}