package com.projectsky.auth_microservice.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class ConfirmationCodeUtil {

    private final SecureRandom secureRandom;

    static {
        secureRandom = new SecureRandom();
    }

    public static String generateConfirmationCode() {
        return String.format("%06d",secureRandom.nextInt(999999));
    }
}
