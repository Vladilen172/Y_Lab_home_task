package com.example.Budget;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BudgetManager {
    private Map<Integer, Double> monthlyBudgets;

    public BudgetManager() {
        this.monthlyBudgets = new HashMap<>();
    }

    public void setMonthlyBudget(int userId, double budget) {
        int currentMonth = LocalDate.now().getMonthValue();
        monthlyBudgets.put(userId, budget);
        System.out.println("Месячный бюджет установлен на " + budget + " для пользователя " + userId);
    }

    public Map<Integer, Double> getMonthlyBudgets() {
        return monthlyBudgets;
    }

    public boolean checkBudgetExceeded(int userId, double currentExpenses) {
        Double budget = monthlyBudgets.get(userId);
        if (budget == null) {
            System.out.println("Бюджет не установлен для пользователя " + userId);
            return false;
        }

        if (currentExpenses > budget) {
            System.out.println("Внимание! Превышен месячный бюджет!");
            return true;
        }
        return false;
    }
}
