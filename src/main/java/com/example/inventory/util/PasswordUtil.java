package com.example.inventory.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int BCRYPT_ROUNDS = 12;

    public static String hashPassword(String password) {

        if (password == null || password.isEmpty()) {
            return null;
        }

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt(BCRYPT_ROUNDS)
        );
    }

    public static boolean verifyPassword(
            String password,
            String storedHash) {

        if (password == null ||
                password.isEmpty() ||
                storedHash == null ||
                storedHash.isEmpty()) {

            return false;
        }

        try {

            return BCrypt.checkpw(
                    password,
                    storedHash
            );

        } catch (IllegalArgumentException e) {

            return false;
        }
    }
}