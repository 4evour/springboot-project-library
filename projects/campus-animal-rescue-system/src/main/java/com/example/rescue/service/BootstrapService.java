package com.example.rescue.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapService implements CommandLineRunner {
    private final UserService userService;

    public BootstrapService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.ensureDefaults();
    }
}
