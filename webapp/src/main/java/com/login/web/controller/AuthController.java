package com.login.web.controller;

import com.login.model.User;
import com.login.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthController handles all authentication-related web requests
 */
@Controller
public class AuthController {

    private static final String SESSION_USER_KEY = "AUTHENTICATED_USERNAME";

    @Autowired
    private AuthenticationService authService;

    /**
     * GET index page
     */
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User currentUser = getCurrentUserFromSession(session);
        model.addAttribute("isLoggedIn", currentUser != null);
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
        }
        return "index";
    }

    /**
     * GET login page
     */
    @GetMapping("/login")
    public String loginPage(Model model, HttpSession session) {
        model.addAttribute("isLoggedIn", getCurrentUserFromSession(session) != null);
        return "login";
    }

    /**
     * POST login endpoint
     */
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username,
                                                       @RequestParam String password,
                                                       HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User user = authService.login(username, password);
        if (user != null) {
            session.setAttribute(SESSION_USER_KEY, user.getUsername());
            response.put("success", true);
            response.put("message", "Login successful!");
            response.put("user", user);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid username or password!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * GET register page
     */
    @GetMapping("/register")
    public String registerPage(Model model, HttpSession session) {
        model.addAttribute("isLoggedIn", getCurrentUserFromSession(session) != null);
        return "register";
    }

    /**
     * POST register endpoint
     */
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> register(@RequestParam String username,
                                                         @RequestParam String password,
                                                         @RequestParam String email,
                                                         @RequestParam String fullName) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Username cannot be empty!");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (password == null || password.length() < 5) {
            response.put("success", false);
            response.put("message", "Password must be at least 5 characters!");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (email == null || !email.contains("@")) {
            response.put("success", false);
            response.put("message", "Invalid email format!");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (fullName == null || fullName.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Full name cannot be empty!");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (authService.register(username, password, email, fullName)) {
            response.put("success", true);
            response.put("message", "Registration successful! You can now login.");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Username already exists!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * GET dashboard page (requires login)
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        User currentUser = getCurrentUserFromSession(session);
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("isLoggedIn", true);
        model.addAttribute("currentUser", currentUser);
        return "dashboard";
    }

    /**
     * GET user profile API endpoint
     */
    @GetMapping("/api/profile")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getProfile(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User currentUser = getCurrentUserFromSession(session);
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "Not logged in!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("success", true);
        response.put("user", currentUser);
        return ResponseEntity.ok(response);
    }

    /**
     * POST profile update endpoint
     */
    @PostMapping("/api/profile")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestParam String fullName,
                                                              @RequestParam String email,
                                                              HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User currentUser = getCurrentUserFromSession(session);
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "Not logged in!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        boolean updated = authService.updateProfile(currentUser.getUsername(), fullName, email);
        if (!updated) {
            response.put("success", false);
            response.put("message", "Invalid profile information.");
            return ResponseEntity.badRequest().body(response);
        }

        User refreshedUser = authService.getUserInfo(currentUser.getUsername());
        response.put("success", true);
        response.put("message", "Profile updated successfully!");
        response.put("user", refreshedUser);
        return ResponseEntity.ok(response);
    }

    /**
     * POST password change endpoint
     */
    @PostMapping("/api/change-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> changePassword(@RequestParam String oldPassword,
                                                               @RequestParam String newPassword,
                                                               @RequestParam String confirmPassword,
                                                               HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User currentUser = getCurrentUserFromSession(session);
        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "Not logged in!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        
        if (!newPassword.equals(confirmPassword)) {
            response.put("success", false);
            response.put("message", "Passwords do not match!");
            return ResponseEntity.badRequest().body(response);
        }

        if (authService.changePassword(currentUser.getUsername(), oldPassword, newPassword)) {
            response.put("success", true);
            response.put("message", "Password changed successfully!");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Current password is incorrect or password too short!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * POST logout endpoint
     */
    @PostMapping("/api/logout")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> logoutApi(HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        User currentUser = getCurrentUserFromSession(session);
        if (currentUser != null) {
            authService.logout(currentUser.getUsername());
        }
        session.invalidate();

        response.put("success", true);
        response.put("message", "Logged out successfully!");
        return ResponseEntity.ok(response);
    }

    /**
     * GET logout page
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        User currentUser = getCurrentUserFromSession(session);
        if (currentUser != null) {
            authService.logout(currentUser.getUsername());
        }
        session.invalidate();
        return "redirect:/";
    }

    private User getCurrentUserFromSession(HttpSession session) {
        Object usernameObj = session.getAttribute(SESSION_USER_KEY);
        if (!(usernameObj instanceof String)) {
            return null;
        }
        return authService.getUserInfo((String) usernameObj);
    }
}
