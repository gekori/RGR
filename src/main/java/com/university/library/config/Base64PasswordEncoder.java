package com.university.library.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Base64;

public class Base64PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        // Кодуємо пароль у Base64
        return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Перевіряємо, чи збігається введений пароль із збереженим
        String encodedRaw = encode(rawPassword);
        return encodedRaw.equals(encodedPassword);
    }
}