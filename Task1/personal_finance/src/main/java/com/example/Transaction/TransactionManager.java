package com.example.Transaction;

import java.util.HashMap;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionManager {
    private HashMap<Integer, LinkedList<Transaction>> transactionEveryoneUsers = new HashMap<>();
    private int id = 1_000_000;

    public HashMap<Integer, LinkedList<Transaction>> getTransactionEveryoneUsers() {
        return transactionEveryoneUsers;
    }

    public void setTransactionEveryoneUsers(HashMap<Integer, LinkedList<Transaction>> transactionEveryoneUsers) {
        this.transactionEveryoneUsers = transactionEveryoneUsers;
    }

    public void addTransaction(int userId, double amount, String category, LocalDate date, String description,
                                  TypeTransaction type) {
        Transaction transaction = new Transaction(id++, userId, amount, category, date, description, type);
        transactionEveryoneUsers.computeIfAbsent(userId, k -> new LinkedList<>()).add(transaction);
    }

    public boolean deleteTransaction(int userId, int transactionId) {
        LinkedList<Transaction> transactions = transactionEveryoneUsers.get(userId);
        if (transactions == null) {
            return false;
        }

        return transactions.removeIf(transaction -> transaction.getId() == transactionId);
    }

    public boolean editTransaction(int userId, int transactionId, double newAmount, String newCategory, String newDescription) {
        LinkedList<Transaction> transactions = transactionEveryoneUsers.get(userId);
        if (transactions == null) {
            return false;
        }

        Transaction transaction = transactions.stream()
                .filter(t -> t.getId() == transactionId)
                .findFirst()
                .orElse(null);
        if (transaction == null) {
            return false;
        }

        transaction.setAmount(newAmount);
        transaction.setCategory(newCategory);
        transaction.setDescription(newDescription);

        return true;
    }

    public List<Transaction> viewTransactions(int userId, LocalDate startDate, LocalDate endDate, String category, TypeTransaction type) {
        LinkedList<Transaction> transactions = transactionEveryoneUsers.get(userId);
        if (transactions == null) {
            return new LinkedList<>();
        }

        return transactions.stream()
                .filter(t -> (startDate == null || !t.getDate().isBefore(startDate)) &&
                        (endDate == null || !t.getDate().isAfter(endDate)) &&
                        (category == null || t.getCategory().equalsIgnoreCase(category)) &&
                        (type == null || t.getType() == type))
                .collect(Collectors.toList());
    }
}
