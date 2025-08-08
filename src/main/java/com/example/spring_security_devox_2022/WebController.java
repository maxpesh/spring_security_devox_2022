package com.example.spring_security_devox_2022;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WebController {
    @GetMapping("/")
    public String publicPage() {
        return "It's a public page";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "It's a private page";
    }
}
