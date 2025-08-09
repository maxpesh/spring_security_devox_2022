package com.example.spring_security_devox_2022;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
class WebController {
    @GetMapping("/")
    String publicPage() {
        return "It's a public page";
    }

    @GetMapping("/private")
    String privatePage(Authentication auth) {
        return String.format("Welcome to the private page %s!", getUserName(auth));
    }

    private String getUserName(Authentication auth) {
        return Optional.of(auth.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(auth::getName);
    }
}
