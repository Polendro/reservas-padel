package com.pgolmeda.padelbooking.infrastructure.adapter.out.security;

import com.pgolmeda.padelbooking.application.port.out.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasherAdapter implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordHasherAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean coincide(String rawPassword, String hash) {
        return passwordEncoder.matches(rawPassword, hash);
    }
}
