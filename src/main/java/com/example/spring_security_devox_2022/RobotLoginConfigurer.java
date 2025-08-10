package com.example.spring_security_devox_2022;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import java.util.ArrayList;
import java.util.List;

class RobotLoginConfigurer extends AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity> {
    private final List<String> passwords = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) throws Exception {
        http.authenticationProvider(new RobotAuthProvider(passwords));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        var authManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new RobotFilter(authManager), AuthorizationFilter.class);
    }

    RobotLoginConfigurer password(String password) {
        this.passwords.add(password);
        return this;
    }
}