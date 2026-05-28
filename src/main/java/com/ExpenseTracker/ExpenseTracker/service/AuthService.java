package com.ExpenseTracker.ExpenseTracker.service;

import com.ExpenseTracker.ExpenseTracker.dto.LoginRequest;
import com.ExpenseTracker.ExpenseTracker.entity.User;
import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
import com.ExpenseTracker.ExpenseTracker.security.JwtService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public AuthService(
            UserRepository userRepository,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    // REGISTER
    public String register(User user) {

        if (userRepository
                .findByEmail(user.getEmail())
                .isPresent()) {

            return "Email already exists";
        }

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN
    public String login(
            LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword());

        if (!matches) {

            return "Invalid password";
        }

        // GENERATE JWT TOKEN
        return jwtService.generateToken(
                user.getEmail());
    }
}