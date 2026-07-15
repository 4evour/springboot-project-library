package com.example.rescue.controller;

import com.example.rescue.common.ApiResponse;
import com.example.rescue.service.AnimalService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AnimalController extends BaseController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animals")
    public ApiResponse<List<Map<String, Object>>> list() {
        return ApiResponse.ok(animalService.list());
    }

    @PostMapping("/admin/animals")
    public ApiResponse<Void> save(@RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        animalService.save(input);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/admin/animals/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        animalService.delete(id);
        return ApiResponse.ok(null);
    }
}
