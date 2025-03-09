package com.example.User;

import java.util.HashMap;

public class UserManager {
    private HashMap<String, User> users = new HashMap<>();
    int id = 1_000_000;

    public HashMap<String, User> getUsers() {
        return users;
    }

    public boolean register(String firstName, String lastName, String email, String password) {
        if (users.containsKey(email)) {
            return false;
        }
        users.put(email, new User(id, firstName, lastName, email, password));
        id++;
        return true;
    }

    public boolean login(String email, String password) {
        User user = users.get(email);
        return (user != null && user.checkPassword(password));
    }

    public boolean deleteAccount(String email, String password) {
        User user = users.get(email);
        if (user == null || !user.checkPassword(password)) {
            return false;
        }
        users.remove(email);
        return true;
    }

    public boolean changeFirstAndLastName(String email, String newFirstName, String newLastName) {
        User user = users.get(email);
        if (user == null) {
            return false;
        }
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        return true;
    }

    public boolean changeEmail(String email, String newEmail) {
        User user = users.get(email);
        if (user == null || users.containsKey(newEmail)) {
            return false;
        }

        user.setEmail(newEmail);
        users.remove(email);
        users.put(newEmail, user);

        return true;
    }

    public boolean changePassword(String email, String newPassword) {
        User user = users.get(email);
        if (user == null) {
            return false;
        }
        user.setPassword(newPassword);
        return true;
    }
}
