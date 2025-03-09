package com.example.Transaction;

import java.time.LocalDate;

public class Transaction {
    private final int id;
    private final int userId;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;
    private TypeTransaction typeTransaction;

    public Transaction(int id, int userId, double amount, String category, LocalDate date, String description, TypeTransaction type) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
        this.typeTransaction = type;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeTransaction getType() {
        return typeTransaction;
    }

    public void setType(TypeTransaction type) {
        this.typeTransaction = type;
    }
}
