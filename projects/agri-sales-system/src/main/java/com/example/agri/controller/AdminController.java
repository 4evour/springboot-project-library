package com.example.agri.controller;

import com.example.agri.common.ApiResponse;
import com.example.agri.service.UserService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController extends BaseController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponse<List<Map<String, Object>>> users(HttpSession session) {
        requireAdmin(session);
        return ApiResponse.ok(userService.listUsers());
    }

    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        userService.updateStatus(id, text(input, "status"));
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        userService.deleteUser(id);
        return ApiResponse.ok(null);
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }
}
