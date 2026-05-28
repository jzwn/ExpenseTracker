package com.expensetracker.backend.controller;

import com.expensetracker.backend.dto.LoginRequest;
import com.expensetracker.backend.dto.RegisterRequest;
import com.expensetracker.backend.model.User;
import com.expensetracker.backend.repository.UserRepository;
import com.expensetracker.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email already registered"));
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMonthlyIncome(request.getMonthlyIncome());

        User saved = userRepository.save(user);
        String token = jwtUtil.generateToken(saved.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "name", saved.getName(),
                "email", saved.getEmail(),
                "monthlyIncome", saved.getMonthlyIncome() != null ? saved.getMonthlyIncome() : 0.0
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getEmail());
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "name", user.getName(),
                            "email", user.getEmail(),
                            "monthlyIncome", user.getMonthlyIncome() != null ? user.getMonthlyIncome() : 0.0
                    ));
                })
                .orElse(ResponseEntity.status(401)
                        .body(Map.of("error", "Invalid email or password")));
    }
}