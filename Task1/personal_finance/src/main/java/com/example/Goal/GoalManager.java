package com.example.Goal;

import java.util.HashMap;
import java.util.Map;

public class GoalManager {
    private Map<Integer, Double> savingsGoals;

    public GoalManager() {
        this.savingsGoals = new HashMap<>();
    }

    public Map<Integer, Double> getSavingsGoals() {
        return savingsGoals;
    }

    public void setSavingsGoal(int userId, double goal) {
        savingsGoals.put(userId, goal);
        System.out.println("Цель по накоплениям установлена на " + goal + " для пользователя " + userId);
    }

    public void trackGoalProgress(int userId, double currentSavings) {
        Double goal = savingsGoals.get(userId);
        if (goal == null) {
            System.out.println("Цель не установлена для пользователя " + userId);
            return;
        }

        if (currentSavings >= goal) {
            System.out.println("Поздравляем! Вы достигли своей цели по накоплениям!");
        } else {
            System.out.println("Прогресс по цели: " + (currentSavings / goal * 100) + "%");
        }
    }
}
