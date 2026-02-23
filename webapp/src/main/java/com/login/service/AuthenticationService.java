package com.login.service;

import com.login.model.User;
import org.springframework.stereotype.Service;

/**
 * AuthenticationService class for handling login and registration logic
 */
@Service
public class AuthenticationService {
    private final UserDatabase database;

    public AuthenticationService() {
        this.database = new UserDatabase();
    }

    /**
     * Login user with username and password
     */
    public User login(String username, String password) {
        if (database.authenticateUser(username, password)) {
            User user = database.getUser(username);
            System.out.println("✓ Login successful! Welcome, " + user.getFullName());
            return user;
        } else {
            System.out.println("✗ Invalid username or password!");
            return null;
        }
    }

    /**
     * Register a new user
     */
    public boolean register(String username, String password, String email, String fullName) {
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            System.out.println("✗ Username cannot be empty!");
            return false;
        }
        if (password == null || password.length() < 5) {
            System.out.println("✗ Password must be at least 5 characters!");
            return false;
        }
        if (!isValidEmail(email)) {
            System.out.println("✗ Invalid email format!");
            return false;
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            System.out.println("✗ Full name cannot be empty!");
            return false;
        }

        if (database.registerUser(username, password, email, fullName)) {
            System.out.println("✓ Registration successful! You can now login.");
            return true;
        } else {
            System.out.println("✗ Username already exists!");
            return false;
        }
    }

    /**
     * Logout current user
     */
    public void logout(String username) {
        if (username != null) {
            System.out.println("✓ Logged out successfully. Goodbye, " + username + "!");
        }
    }

    /**
     * Get user by username
     */
    public User getUserInfo(String username) {
        return database.getUser(username);
    }

    /**
     * Change password
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (!database.authenticateUser(username, oldPassword)) {
            System.out.println("✗ Current password is incorrect!");
            return false;
        }
        if (newPassword == null || newPassword.length() < 5) {
            System.out.println("✗ New password must be at least 5 characters!");
            return false;
        }
        if (database.updatePassword(username, newPassword)) {
            System.out.println("✓ Password changed successfully!");
            return true;
        }
        return false;
    }

    /**
     * Update profile information
     */
    public boolean updateProfile(String username, String fullName, String email) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        if (!isValidEmail(email)) {
            return false;
        }
        return database.updateProfile(username, fullName, email);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.indexOf('@') > 0 && email.lastIndexOf('.') > email.indexOf('@');
    }
}
