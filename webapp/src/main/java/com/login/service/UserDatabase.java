package com.login.service;

import com.login.model.User;
import java.io.*;
import java.util.*;

/**
 * UserDatabase class for managing user storage and retrieval
 * Uses file-based storage for persistence
 */
public class UserDatabase {
    private static final String DATA_FILE = "data/users.dat";
    private Map<String, User> users;

    public UserDatabase() {
        this.users = new HashMap<>();
        loadUsers();
        // Initialize with default users if database is empty
        if (users.isEmpty()) {
            initializeDefaultUsers();
        }
    }

    /**
     * Initialize default users for testing
     */
    private void initializeDefaultUsers() {
        users.put("admin", new User("admin", "admin123", "admin@example.com", "Administrator"));
        users.put("john", new User("john", "john123", "john@example.com", "John Doe"));
        users.put("jane", new User("jane", "jane123", "jane@example.com", "Jane Smith"));
        saveUsers();
    }

    /**
     * Register a new user
     */
    public boolean registerUser(String username, String password, String email, String fullName) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }
        User newUser = new User(username, password, email, fullName);
        users.put(username, newUser);
        saveUsers();
        return true;
    }

    /**
     * Authenticate a user
     */
    public boolean authenticateUser(String username, String password) {
        if (!users.containsKey(username)) {
            return false; // User not found
        }
        User user = users.get(username);
        return user.getPassword().equals(password) && user.isActive();
    }

    /**
     * Get user by username
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Check if user exists
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Get all users
     */
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Save users to file
     */
    private void saveUsers() {
        try {
            File file = new File(DATA_FILE);
            file.getParentFile().mkdirs();
            
            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(file))) {
                oos.writeObject(users);
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    /**
     * Load users from file
     */
    @SuppressWarnings("unchecked")
    private void loadUsers() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(file))) {
                    users = (Map<String, User>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading users: " + e.getMessage());
            users = new HashMap<>();
        }
    }

    /**
     * Delete a user
     */
    public boolean deleteUser(String username) {
        if (users.remove(username) != null) {
            saveUsers();
            return true;
        }
        return false;
    }

    /**
     * Update user password
     */
    public boolean updatePassword(String username, String newPassword) {
        if (users.containsKey(username)) {
            users.get(username).setPassword(newPassword);
            saveUsers();
            return true;
        }
        return false;
    }

    /**
     * Update user profile information
     */
    public boolean updateProfile(String username, String fullName, String email) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            user.setFullName(fullName);
            user.setEmail(email);
            saveUsers();
            return true;
        }
        return false;
    }
}
