package com.customer.service.section17.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.customer.service.section17.constant.CustomerConstant.*;

/**
 * Util class for customer-related helper methods such as hashing passwords.
 * Uses SHA-256 hashing instead of BCrypt.
 */
public class CustomerUtil {

    public static String generatePassword() {
        SecureRandom random = new SecureRandom();
        return random.ints(PASS_LENGTH, 0, PASS_CHARACTERS.length())
                .mapToObj(PASS_CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public static String generateOtp(){
        SecureRandom random = new SecureRandom();
        return random.ints(OTP_LENGTH, 0, OTP_CHARACTERS.length())
                .mapToObj(OTP_CHARACTERS::charAt)
                .map(Objects::toString)
                .collect(Collectors.joining());
    }

    /**
     * Hashes the given password using SHA-256 algorithm.
     *
     * @return hashed password in hexadecimal format
     */
    public static String autoGenerateHashPassword() {
        String plainPassword = generatePassword();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(plainPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: SHA-256 algorithm not found.", e);
        }
    }
}
