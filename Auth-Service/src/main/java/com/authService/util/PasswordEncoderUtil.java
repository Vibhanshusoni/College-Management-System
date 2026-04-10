package com.authService.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {

    private final BCryptPasswordEncoder encoder;

    public PasswordEncoderUtil() {
        this.encoder = new BCryptPasswordEncoder(12); // Strength: 12
    }

    /**
     * Encode a plain text password
     */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * Verify if a raw password matches the encoded password
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Get the password encoder instance
     */
    public BCryptPasswordEncoder getEncoder() {
        return encoder;
    }
}
