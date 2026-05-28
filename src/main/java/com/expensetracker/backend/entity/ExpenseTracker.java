package com.expensetracker.backend.entity;

import com.expensetracker.backend.model.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ExpenseTracker {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double amount;

    private String category;

    private LocalDate date;

    private String description;

    // MANY EXPENSES BELONG TO ONE USER
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ExpenseTracker() {
    }

    public ExpenseTracker(
            Long id,
            String title,
            Double amount,
            String category,
            LocalDate date,
            String description,
            User user) {

        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title) {

        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(
            Double amount) {

        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(
            String category) {

        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(
            LocalDate date) {

        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(
            User user) {

        this.user = user;
    }
}
