package com.example;

import com.example.User.*;
import com.example.Transaction.*;
import com.example.Budget.*;
import com.example.Goal.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        UserManager userManager = new UserManager();
        TransactionManager transactionManager = new TransactionManager();
        BudgetManager budgetManager = new BudgetManager();
        GoalManager goalManager = new GoalManager();

        while (true) {
            printStartMenu();
            String choiceInput = reader.readLine().trim();

            if (choiceInput.isEmpty()) {
                System.out.println("Ошибка: Введите число от 1 до 3.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат ввода. Пожалуйста, введите число от 1 до 3.");
                continue;
            }

            if (choice == 3) {
                System.out.println("Выход из программы.");
                break;
            }

            User currentUser = null;

            if (choice == 1) {
                currentUser = registerUser(reader, userManager);
            } else if (choice == 2) {
                currentUser = loginUser(reader, userManager);
            }

            if (currentUser != null) {
                manageAccount(reader, currentUser, transactionManager, budgetManager, userManager, goalManager);
            } else {
                System.out.println("Неверные данные для входа. Попробуйте снова.");
            }
            System.out.println();
        }
    }

    private static User registerUser(BufferedReader reader, UserManager manager) throws IOException {
        System.out.print("Введите имя: ");
        String firstName = reader.readLine();

        System.out.print("Введите фамилию: ");
        String lastName = reader.readLine();

        System.out.print("Введите почту: ");
        String email = reader.readLine();

        System.out.print("Введите пароль: ");
        String password = reader.readLine();

        if (manager.register(firstName, lastName, email, password)) {
            System.out.println("Пользователь зарегистрировался!");
            return manager.getUsers().get(email);
        } else {
            System.out.println("Регистрация не удалась. Пользователь с такой почтой уже существует.");
            return null;
        }
    }

    private static void printStartMenu() {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        System.out.println("| Добро пожаловать в систему управления финансами!         |");
        System.out.println("| Пожалуйста, выберите действие:                           |");
        System.out.println("| 1. Регистрация                                           |");
        System.out.println("| 2. Вход                                                  |");
        System.out.println("| 3. Выйти из приложения                                   |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static void printAccountMenu() {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        System.out.println("| Управление учетной записью:                              |");
        System.out.println("| Пожалуйста, выберите действие:                           |");
        System.out.println("| 1. Редактировать учетную запись                          |");
        System.out.println("| 2. Управление транзакциями                               |");
        System.out.println("| 3. Управление бюджетом                                  |");
        System.out.println("| 4. Управление целями                                    |");
        System.out.println("| 5. Выйти в главное меню                                  |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static void printEditAccountMenu() {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        System.out.println("| Редактирование учетной записи:                            |");
        System.out.println("| Пожалуйста, выберите параметр для изменения:             |");
        System.out.println("| 1. Изменить имя                                          |");
        System.out.println("| 2. Изменить фамилию                                      |");
        System.out.println("| 3. Изменить email                                         |");
        System.out.println("| 4. Изменить пароль                                       |");
        System.out.println("| 5. Удалить учетную запись                                |");
        System.out.println("| 6. Вернуться назад                                        |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static void manageAccount(BufferedReader reader, User user, TransactionManager transactionManager, BudgetManager budgetManager, UserManager userManager, GoalManager goalManager) throws IOException {
        while (true) {
            printAccountMenu();
            String choiceInput = reader.readLine().trim();

            if (choiceInput.isEmpty()) {
                System.out.println("Ошибка: Введите число от 1 до 5.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат ввода. Пожалуйста, введите число от 1 до 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    editUserAccount(reader, user, userManager);
                    break;
                case 2:
                    manageTransactions(reader, user, transactionManager, budgetManager);
                    break;
                case 3:
                    manageBudget(reader, user, budgetManager, transactionManager);
                    break;
                case 4:
                    manageGoals(reader, user, goalManager, transactionManager);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void editUserAccount(BufferedReader reader, User user, UserManager manager) throws IOException {
        while (true) {
            printEditAccountMenu();
            int choice = Integer.parseInt(reader.readLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Введите новое имя: ");
                    String newFirstName = reader.readLine().trim();
                    user.setFirstName(newFirstName);
                    System.out.println("Имя успешно изменено.");
                    break;
                case 2:
                    System.out.print("Введите новую фамилию: ");
                    String newLastName = reader.readLine().trim();
                    user.setLastName(newLastName);
                    System.out.println("Фамилия успешно изменена.");
                    break;
                case 3:
                    System.out.print("Введите новый email: ");
                    String newEmail = reader.readLine().trim();
                    if (manager.changeEmail(user.getEmail(), newEmail)) {
                        System.out.println("Email успешно изменен.");
                    } else {
                        System.out.println("Не удалось изменить email.");
                    }
                    break;
                case 4:
                    System.out.print("Введите новый пароль: ");
                    String newPassword = reader.readLine().trim();
                    user.setPassword(newPassword);
                    System.out.println("Пароль успешно изменен.");
                    break;
                case 5:
                    System.out.print("Вы уверены, что хотите удалить учетную запись? (да/нет): ");
                    String confirmation = reader.readLine().trim().toLowerCase();
                    if (confirmation.equals("да")) {
                        if (manager.deleteAccount(user.getEmail(), user.getPassword())) {
                            System.out.println("Учетная запись успешно удалена.");
                            return;
                        } else {
                            System.out.println("Не удалось удалить учетную запись.");
                        }
                    } else {
                        System.out.println("Удаление учетной записи отменено.");
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printTransactionMenu(User user) {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.println("| ID пользователя: " + user.getId());
        System.out.println("| Добро пожаловать, " + user.getFirstName() + " " + user.getLastName());
        System.out.println("| Управление транзакциями                                  |");
        System.out.println("|                                                          |");
        System.out.println("| Пожалуйста, выберите действие:                           |");
        System.out.println("| 1. Создать транзакцию                                    |");
        System.out.println("| 2. Редактировать транзакцию                              |");
        System.out.println("| 3. Удалить транзакцию                                    |");
        System.out.println("| 4. Просмотреть транзакции                                |");
        System.out.println("| 5. Выйти в главное меню                                  |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static User loginUser(BufferedReader reader, UserManager manager) throws IOException {
        System.out.print("Введите почту: ");
        String email = reader.readLine();

        System.out.print("Введите пароль: ");
        String password = reader.readLine();

        if (manager.login(email, password)) {
            System.out.println("Вход выполнен успешно!");
            return manager.getUsers().get(email);
        } else {
            return null;
        }
    }

    private static void manageTransactions(BufferedReader reader, User user, TransactionManager transactionManager, BudgetManager budgetManager) throws IOException {
        while (true) {
            printTransactionMenu(user);
            String choiceInput = reader.readLine().trim();

            if (choiceInput.isEmpty()) {
                System.out.println("Ошибка: Введите число от 1 до 5.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат ввода. Пожалуйста, введите число от 1 до 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    createTransaction(reader, user, transactionManager);
                    break;
                case 2:
                    editTransaction(reader, user, transactionManager);
                    break;
                case 3:
                    deleteTransaction(reader, user, transactionManager);
                    break;
                case 4:
                    viewTransactions(reader, user, transactionManager);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void createTransaction(BufferedReader reader, User user, TransactionManager transactionManager) throws IOException {
        System.out.print("Введите сумму: ");
        String amountInput = reader.readLine().trim();

        if (amountInput.isEmpty()) {
            System.out.println("Ошибка: Введите сумму.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат суммы.");
            return;
        }

        System.out.print("Введите категорию: ");
        String category = reader.readLine();

        System.out.print("Введите дату (YYYY-MM-DD): ");
        String dateInput = reader.readLine().trim();

        if (dateInput.isEmpty()) {
            System.out.println("Ошибка: Введите дату.");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: Неверный формат даты. Используйте формат YYYY-MM-DD.");
            return;
        }

        System.out.print("Введите описание: ");
        String description = reader.readLine();

        System.out.print("Введите тип (INCOME/EXPENSE): ");
        String typeInput = reader.readLine().trim();

        if (typeInput.isEmpty()) {
            System.out.println("Ошибка: Введите тип транзакции.");
            return;
        }

        TypeTransaction type;
        try {
            type = TypeTransaction.valueOf(typeInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: Неверный тип транзакции. Используйте INCOME или EXPENSE.");
            return;
        }

        transactionManager.addTransaction(user.getId(), amount, category, date, description, type);
        System.out.println("Транзакция создана.");
    }

    private static void editTransaction(BufferedReader reader, User user, TransactionManager transactionManager) throws IOException {
        System.out.print("Введите ID транзакции для редактирования: ");
        String transactionIdInput = reader.readLine().trim();

        if (transactionIdInput.isEmpty()) {
            System.out.println("Ошибка: Введите ID транзакции.");
            return;
        }

        int transactionId;
        try {
            transactionId = Integer.parseInt(transactionIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID транзакции.");
            return;
        }

        System.out.print("Введите новую сумму: ");
        double newAmount = Double.parseDouble(reader.readLine());

        System.out.print("Введите новую категорию: ");
        String newCategory = reader.readLine();

        System.out.print("Введите новое описание: ");
        String newDescription = reader.readLine();

        if (transactionManager.editTransaction(user.getId(), transactionId, newAmount, newCategory, newDescription)) {
            System.out.println("Транзакция отредактирована.");
        } else {
            System.out.println("Не удалось отредактировать транзакцию.");
        }
    }

    private static void deleteTransaction(BufferedReader reader, User user, TransactionManager transactionManager) throws IOException {
        System.out.print("Введите ID транзакции для удаления: ");
        String transactionIdInput = reader.readLine().trim();

        if (transactionIdInput.isEmpty()) {
            System.out.println("Ошибка: Введите ID транзакции.");
            return;
        }

        int transactionId;
        try {
            transactionId = Integer.parseInt(transactionIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Неверный формат ID транзакции.");
            return;
        }

        if (transactionManager.deleteTransaction(user.getId(), transactionId)) {
            System.out.println("Транзакция удалена.");
        } else {
            System.out.println("Не удалось удалить транзакцию.");
        }
    }

    private static void viewTransactions(BufferedReader reader, User user, TransactionManager transactionManager) throws IOException {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        System.out.println("| ID  | Сумма     | Категория | Дата       | Описание          | Тип      |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        for (Transaction transaction : transactionManager.viewTransactions(user.getId(), null, null, null, null)) {
            System.out.printf("| %-4d | %-10.2f | %-10s | %-10s | %-18s | %-8s |\n",
                    transaction.getId(),
                    transaction.getAmount(),
                    transaction.getCategory(),
                    transaction.getDate(),
                    transaction.getDescription(),
                    transaction.getType());
        }

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static void manageBudget(BufferedReader reader, User user, BudgetManager budgetManager, TransactionManager transactionManager) throws IOException {
        while (true) {
            printBudgetMenu();
            String choiceInput = reader.readLine().trim();

            if (choiceInput.isEmpty()) {
                System.out.println("Ошибка: Введите число от 1 до 3.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат ввода. Пожалуйста, введите число от 1 до 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите месячный бюджет: ");
                    double budget = Double.parseDouble(reader.readLine());
                    budgetManager.setMonthlyBudget(user.getId(), budget);
                    break;
                case 2:
                    double currentExpenses = calculateCurrentExpenses(user.getId(), transactionManager);
                    if (!budgetManager.checkBudgetExceeded(user.getId(), currentExpenses)) {
                        System.out.println("Ваши расходы в пределах бюджета.");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printBudgetMenu() {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        System.out.println("| Управление бюджетом:                                     |");
        System.out.printf("| Период: %s - %s                                     |\n", startOfMonth, endOfMonth);
        System.out.println("| Пожалуйста, выберите действие:                           |");
        System.out.println("| 1. Установить месячный бюджет                            |");
        System.out.println("| 2. Проверить превышение бюджета                         |");
        System.out.println("| 3. Вернуться назад                                       |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static double calculateCurrentExpenses(int userId, TransactionManager transactionManager) {
        double totalExpenses = 0;
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);

        for (Transaction transaction : transactionManager.viewTransactions(userId, startOfMonth, now, null, TypeTransaction.EXPENSE)) {
            totalExpenses += transaction.getAmount();
        }
        return totalExpenses;
    }

    private static void manageGoals(BufferedReader reader, User user, GoalManager goalManager, TransactionManager transactionManager) throws IOException {
        while (true) {
            printGoalMenu();
            String choiceInput = reader.readLine().trim();

            if (choiceInput.isEmpty()) {
                System.out.println("Ошибка: Введите число от 1 до 3.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Неверный формат ввода. Пожалуйста, введите число от 1 до 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите цель по накоплениям: ");
                    double goal = Double.parseDouble(reader.readLine());
                    goalManager.setSavingsGoal(user.getId(), goal);
                    break;
                case 2:
                    double currentSavings = calculateCurrentSavings(user.getId(), transactionManager);
                    goalManager.trackGoalProgress(user.getId(), currentSavings);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void printGoalMenu() {
        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();

        System.out.println("| Управление целями:                                       |");
        System.out.println("| Пожалуйста, выберите действие:                           |");
        System.out.println("| 1. Установить цель по накоплениям                        |");
        System.out.println("| 2. Проверить прогресс по цели                            |");
        System.out.println("| 3. Вернуться назад                                       |");

        for (int i = 0; i < 60; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static double calculateCurrentSavings(int userId, TransactionManager transactionManager) {
        double totalSavings = 0;
        for (Transaction transaction : transactionManager.viewTransactions(userId, null, null, null, TypeTransaction.INCOME)) {
            totalSavings += transaction.getAmount();
        }
        for (Transaction transaction : transactionManager.viewTransactions(userId, null, null, null, TypeTransaction.EXPENSE)) {
            totalSavings -= transaction.getAmount();
        }
        return totalSavings;
    }
}
