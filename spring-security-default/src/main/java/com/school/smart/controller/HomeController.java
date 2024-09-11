package com.school.smart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping ("/")
    public String getDefault() {
        return "Default Spring security Application";
    }
}
