package com.example.agri.controller;

import com.example.agri.common.ApiResponse;
import com.example.agri.common.CurrentUser;
import com.example.agri.service.UserService;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public static final String SESSION_USER = "CURRENT_USER";

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<CurrentUser> login(@RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = userService.login(text(input, "username"), text(input, "password"));
        session.setAttribute(SESSION_USER, user);
        return ApiResponse.ok(user);
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody Map<String, Object> input) {
        userService.register(text(input, "username"), text(input, "password"), text(input, "realName"), text(input, "phone"));
        return ApiResponse.ok(null);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpSession session) {
        session.invalidate();
        return ApiResponse.ok(null);
    }

    @GetMapping("/me")
    public ApiResponse<CurrentUser> me(HttpSession session) {
        return ApiResponse.ok((CurrentUser) session.getAttribute(SESSION_USER));
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }
}
