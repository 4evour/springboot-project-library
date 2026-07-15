package com.example.rescue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncodingTest {
    @Test
    void bcryptHashMatchesOriginalPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("123456");

        assertTrue(encoder.matches("123456", hash));
        assertFalse(encoder.matches("wrong", hash));
    }
}
