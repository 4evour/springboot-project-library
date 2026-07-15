package com.example.agri.controller;

import com.example.agri.common.ApiResponse;
import com.example.agri.service.ProductService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController extends BaseController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/categories")
    public ApiResponse<List<Map<String, Object>>> categories() {
        return ApiResponse.ok(productService.listCategories());
    }

    @PostMapping("/admin/categories")
    public ApiResponse<Void> saveCategory(@RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        productService.saveCategory(input);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/admin/categories/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        productService.deleteCategory(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/products")
    public ApiResponse<List<Map<String, Object>>> products(@RequestParam(required = false) Long categoryId, @RequestParam(required = false) String keyword) {
        return ApiResponse.ok(productService.listProducts(categoryId, keyword));
    }

    @PostMapping("/admin/products")
    public ApiResponse<Void> saveProduct(@RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        productService.saveProduct(input);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/admin/products/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        productService.deleteProduct(id);
        return ApiResponse.ok(null);
    }
}
