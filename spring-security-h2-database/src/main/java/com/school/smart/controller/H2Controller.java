package com.school.smart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H2Controller {

    @GetMapping ("/")
    public String getDefault() {
        return "Spring security InMemory Authentication";
    }

    @GetMapping ("/default")
    public String defaultApi() {
        return "Welcome to the default API, accessible to all authenticated users!";
    }

    @GetMapping ("/user")
    public String userApi() {
        return "Hello User! You have access to user-specific features.";
    }

    @GetMapping ("/admin")
    public String adminApi() {
        return "Welcome Admin! You have access to admin-specific features.";
    }
}
