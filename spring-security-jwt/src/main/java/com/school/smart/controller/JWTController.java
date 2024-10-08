package com.school.smart.controller;

import com.school.smart.dto.LoginRequest;
import com.school.smart.dto.LoginResponse;
import com.school.smart.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JWTController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*@GetMapping ("/")
    public String sayHello() {
        return "Spring security with JWT";
    }*/

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

    @PostMapping ("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        LoginResponse response = new LoginResponse(jwtToken, userDetails.getUsername(), roles);
        return ResponseEntity.ok(response);
    }
}
