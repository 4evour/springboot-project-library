package com.example.rescue.controller;

import com.example.rescue.common.ApiResponse;
import com.example.rescue.service.NoticeService;
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
public class NoticeController extends BaseController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notices")
    public ApiResponse<List<Map<String, Object>>> list() {
        return ApiResponse.ok(noticeService.list());
    }

    @PostMapping("/admin/notices")
    public ApiResponse<Void> save(@RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        noticeService.save(input);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/admin/notices/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        noticeService.delete(id);
        return ApiResponse.ok(null);
    }
}
