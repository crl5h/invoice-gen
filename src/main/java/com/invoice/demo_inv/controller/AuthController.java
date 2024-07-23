package com.invoice.demo_inv.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invoice.demo_inv.entity.User;
import com.invoice.demo_inv.service.AuthService;
import com.invoice.demo_inv.service.UserService;
import com.invoice.demo_inv.utils.LoginRequest;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated LoginRequest request) {
        return authService.loginUser(request.getEmail(), request.getPassword());
    }

    @PostMapping("/signup")
    public void signup(@RequestBody User userEntity) {
        userService.addUser(userEntity);
    }
}
