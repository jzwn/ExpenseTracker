package com.expensetracker.backend.service;

import com.expensetracker.backend.entity.ExpenseTracker;
import com.expensetracker.backend.repository.ExpenseTrackerRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseTrackerService {

    private final ExpenseTrackerRepository expenseTrackerRepository;

    public ExpenseTrackerService(
            ExpenseTrackerRepository expenseTrackerRepository) {

        this.expenseTrackerRepository = expenseTrackerRepository;
    }

    // ADD
    public ExpenseTracker addExpense(
            ExpenseTracker expenseTracker) {

        return expenseTrackerRepository.save(expenseTracker);
    }

    // GET ALL
    public List<ExpenseTracker> getAllExpenses() {

        return expenseTrackerRepository.findAll();
    }

    // GET BY ID
    public ExpenseTracker getExpenseById(Long id) {

        return expenseTrackerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));
    }

    // UPDATE
    public ExpenseTracker updateExpense(
            Long id,
            ExpenseTracker updatedExpense) {

        ExpenseTracker existingExpense =
                getExpenseById(id);

        existingExpense.setTitle(updatedExpense.getTitle());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setCategory(updatedExpense.getCategory());
        existingExpense.setDate(updatedExpense.getDate());
        existingExpense.setDescription(updatedExpense.getDescription());

        return expenseTrackerRepository.save(existingExpense);
    }

    // DELETE
    public String deleteExpense(Long id) {

        expenseTrackerRepository.deleteById(id);

        return "Expense deleted successfully";
    }

    // FILTER CATEGORY
    public List<ExpenseTracker> getExpensesByCategory(
            String category) {

        return expenseTrackerRepository
                .findByCategory(category);
    }

    // TOTAL
    public Double getTotalExpenses(
            LocalDate start,
            LocalDate end) {

        List<ExpenseTracker> expenses =
                expenseTrackerRepository
                        .findByDateBetween(start, end);

        return expenses.stream()
                .mapToDouble(ExpenseTracker::getAmount)
                .sum();
    }

    // GET EXPENSES BY MONTH
    public List<ExpenseTracker>
    getExpensesByMonth(

            LocalDate start,
            LocalDate end) {

        return expenseTrackerRepository
                .findByDateBetween(start, end);
    }
}

