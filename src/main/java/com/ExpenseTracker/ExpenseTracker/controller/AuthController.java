package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.dto.LoginRequest;
import com.ExpenseTracker.ExpenseTracker.entity.User;
import com.ExpenseTracker.ExpenseTracker.service.AuthService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(
            @RequestBody User user) {

        return authService.register(user);
    }

    // LOGIN
    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}