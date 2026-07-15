package com.example.agri.controller;

import com.example.agri.common.ApiResponse;
import com.example.agri.common.CurrentUser;
import com.example.agri.service.CartOrderService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartOrderController extends BaseController {
    private final CartOrderService cartOrderService;

    public CartOrderController(CartOrderService cartOrderService) {
        this.cartOrderService = cartOrderService;
    }

    @GetMapping("/cart")
    public ApiResponse<List<Map<String, Object>>> cart(HttpSession session) {
        CurrentUser user = requireLogin(session);
        return ApiResponse.ok(cartOrderService.listCart(user.getId()));
    }

    @PostMapping("/cart")
    public ApiResponse<Void> addCart(@RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = requireLogin(session);
        cartOrderService.addCart(user.getId(), longValue(input.get("productId")), intValue(input.get("quantity")));
        return ApiResponse.ok(null);
    }

    @PutMapping("/cart/{id}")
    public ApiResponse<Void> updateCart(@PathVariable Long id, @RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = requireLogin(session);
        cartOrderService.updateCart(user.getId(), id, intValue(input.get("quantity")));
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/cart/{id}")
    public ApiResponse<Void> deleteCart(@PathVariable Long id, HttpSession session) {
        CurrentUser user = requireLogin(session);
        cartOrderService.deleteCart(user.getId(), id);
        return ApiResponse.ok(null);
    }

    @PostMapping("/orders/checkout")
    public ApiResponse<Long> checkout(@RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = requireLogin(session);
        return ApiResponse.ok(cartOrderService.checkout(user.getId(), input));
    }

    @GetMapping("/orders/mine")
    public ApiResponse<List<Map<String, Object>>> myOrders(HttpSession session) {
        CurrentUser user = requireLogin(session);
        return ApiResponse.ok(cartOrderService.listMyOrders(user.getId()));
    }

    @GetMapping("/admin/orders")
    public ApiResponse<List<Map<String, Object>>> allOrders(HttpSession session) {
        requireAdmin(session);
        return ApiResponse.ok(cartOrderService.listAllOrders());
    }

    @PutMapping("/admin/orders/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        cartOrderService.updateOrderStatus(id, text(input, "status"));
        return ApiResponse.ok(null);
    }

    private Long longValue(Object value) {
        return value == null ? null : Long.valueOf(String.valueOf(value));
    }

    private Integer intValue(Object value) {
        return value == null ? null : Integer.valueOf(String.valueOf(value));
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }
}
