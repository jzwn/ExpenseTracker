package com.ExpenseTracker.backend.repository;

import com.ExpenseTracker.backend.entity.ExpenseTracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseTrackerRepository
        extends JpaRepository<ExpenseTracker, Long> {

    List<ExpenseTracker> findByCategory(String category);

    List<ExpenseTracker> findByDateBetween(
            LocalDate start,
            LocalDate end);
}

