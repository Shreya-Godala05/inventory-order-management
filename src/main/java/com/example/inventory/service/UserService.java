package com.example.inventory.service;

import com.example.inventory.model.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.util.PasswordUtil;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public User findUserByUsername(String username) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        return userRepository.findByUsername(username.trim());
    }

    public User authenticate(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        if (password == null || password.isEmpty()) {
            return null;
        }

        User user = findUserByUsername(username);

        if (user == null) {
            return null;
        }

        if (PasswordUtil.verifyPassword(
                password,
                user.getPasswordHash())) {

            return user;
        }

        return null;
    }

    public boolean isAdmin(User user) {
        return user != null &&
                "ADMIN".equalsIgnoreCase(user.getRole());
    }

    public boolean isStaff(User user) {
        return user != null &&
                "STAFF".equalsIgnoreCase(user.getRole());
    }
}