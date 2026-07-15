package com.example.rescue.controller;

import com.example.rescue.common.ApiResponse;
import com.example.rescue.common.CurrentUser;
import com.example.rescue.service.RecordService;
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
public class RecordController extends BaseController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/rescues/mine")
    public ApiResponse<List<Map<String, Object>>> myRescue(HttpSession session) {
        CurrentUser user = requireLogin(session);
        return ApiResponse.ok(recordService.listMyRescue(user.getId()));
    }

    @GetMapping("/admin/rescues")
    public ApiResponse<List<Map<String, Object>>> allRescue(HttpSession session) {
        requireAdmin(session);
        return ApiResponse.ok(recordService.listAllRescue());
    }

    @PostMapping("/rescues")
    public ApiResponse<Void> submitRescue(@RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = requireLogin(session);
        recordService.submitRescue(user.getId(), input);
        return ApiResponse.ok(null);
    }

    @PutMapping("/admin/rescues/{id}/status")
    public ApiResponse<Void> rescueStatus(@PathVariable Long id, @RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        recordService.updateRescueStatus(id, text(input, "status"));
        return ApiResponse.ok(null);
    }

    @GetMapping("/adoptions/mine")
    public ApiResponse<List<Map<String, Object>>> myAdoptions(HttpSession session) {
        CurrentUser user = requireLogin(session);
        return ApiResponse.ok(recordService.listMyAdoptions(user.getId()));
    }

    @GetMapping("/admin/adoptions")
    public ApiResponse<List<Map<String, Object>>> allAdoptions(HttpSession session) {
        requireAdmin(session);
        return ApiResponse.ok(recordService.listAllAdoptions());
    }

    @PostMapping("/adoptions")
    public ApiResponse<Void> submitAdoption(@RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = requireLogin(session);
        recordService.submitAdoption(user.getId(), input);
        return ApiResponse.ok(null);
    }

    @PutMapping("/admin/adoptions/{id}/status")
    public ApiResponse<Void> adoptionStatus(@PathVariable Long id, @RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        recordService.updateAdoptionStatus(id, text(input, "status"));
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/admin/adoptions/{id}")
    public ApiResponse<Void> deleteAdoption(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        recordService.deleteAdoption(id);
        return ApiResponse.ok(null);
    }

    @GetMapping("/feedback/mine")
    public ApiResponse<List<Map<String, Object>>> myFeedback(HttpSession session) {
        CurrentUser user = requireLogin(session);
        return ApiResponse.ok(recordService.listMyFeedback(user.getId()));
    }

    @GetMapping("/admin/feedback")
    public ApiResponse<List<Map<String, Object>>> allFeedback(HttpSession session) {
        requireAdmin(session);
        return ApiResponse.ok(recordService.listAllFeedback());
    }

    @PostMapping("/feedback")
    public ApiResponse<Void> submitFeedback(@RequestBody Map<String, Object> input, HttpSession session) {
        CurrentUser user = requireLogin(session);
        recordService.submitFeedback(user.getId(), text(input, "content"));
        return ApiResponse.ok(null);
    }

    @PutMapping("/admin/feedback/{id}/reply")
    public ApiResponse<Void> replyFeedback(@PathVariable Long id, @RequestBody Map<String, Object> input, HttpSession session) {
        requireAdmin(session);
        recordService.replyFeedback(id, text(input, "reply"));
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/admin/feedback/{id}")
    public ApiResponse<Void> deleteFeedback(@PathVariable Long id, HttpSession session) {
        requireAdmin(session);
        recordService.deleteFeedback(id);
        return ApiResponse.ok(null);
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }
}
