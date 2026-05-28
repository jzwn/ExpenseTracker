package com.ExpenseTracker.ExpenseTracker.repository;

import com.ExpenseTracker.ExpenseTracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}