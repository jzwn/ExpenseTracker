package com.ExpenseTracker.ExpenseTracker.controller;

import com.ExpenseTracker.ExpenseTracker.entity.ExpenseTracker;
import com.ExpenseTracker.ExpenseTracker.service.ExpenseTrackerService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseTrackerController {

    private final ExpenseTrackerService expenseTrackerService;

    public ExpenseTrackerController(
            ExpenseTrackerService expenseTrackerService) {

        this.expenseTrackerService = expenseTrackerService;
    }

    // ADD
    @PostMapping
    public ExpenseTracker addExpense(
            @RequestBody ExpenseTracker expenseTracker) {

        return expenseTrackerService
                .addExpense(expenseTracker);
    }

    // GET ALL
    @GetMapping
    public List<ExpenseTracker> getAllExpenses() {

        return expenseTrackerService.getAllExpenses();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ExpenseTracker getExpenseById(
            @PathVariable Long id) {

        return expenseTrackerService.getExpenseById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ExpenseTracker updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseTracker expenseTracker) {

        return expenseTrackerService
                .updateExpense(id, expenseTracker);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id) {

        return expenseTrackerService.deleteExpense(id);
    }

    // FILTER CATEGORY
    @GetMapping("/category/{category}")
    public List<ExpenseTracker> getExpensesByCategory(
            @PathVariable String category) {

        return expenseTrackerService
                .getExpensesByCategory(category);
    }

    // TOTAL
    @GetMapping("/total")
    public Double getTotalExpenses(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return expenseTrackerService
                .getTotalExpenses(start, end);
    }

    // GET EXPENSES BY MONTH
    @GetMapping("/month")
    public List<ExpenseTracker>
    getExpensesByMonth(

            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return expenseTrackerService
                .getExpensesByMonth(start, end);
    }
}