package com.example.rescue.service;

import com.example.rescue.mapper.AdoptionMapper;
import com.example.rescue.mapper.FeedbackMapper;
import com.example.rescue.mapper.RescueMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RecordService {
    private final RescueMapper rescueMapper;
    private final AdoptionMapper adoptionMapper;
    private final FeedbackMapper feedbackMapper;

    public RecordService(RescueMapper rescueMapper, AdoptionMapper adoptionMapper, FeedbackMapper feedbackMapper) {
        this.rescueMapper = rescueMapper;
        this.adoptionMapper = adoptionMapper;
        this.feedbackMapper = feedbackMapper;
    }

    public List<Map<String, Object>> listAllRescue() {
        return rescueMapper.listAll();
    }

    public List<Map<String, Object>> listMyRescue(Long userId) {
        return rescueMapper.listByUser(userId);
    }

    public void submitRescue(Long userId, Map<String, Object> input) {
        String title = text(input, "title");
        String location = text(input, "location");
        String description = text(input, "description");
        if (!StringUtils.hasText(title) || !StringUtils.hasText(location) || !StringUtils.hasText(description)) {
            throw new IllegalArgumentException("救助标题、地点和描述不能为空");
        }
        Long animalId = longValue(input.get("animalId"));
        rescueMapper.insert(userId, animalId, title, location, description);
    }

    public void updateRescueStatus(Long id, String status) {
        if (!"SUBMITTED".equals(status) && !"PROCESSING".equals(status) && !"DONE".equals(status)) {
            throw new IllegalArgumentException("救助状态不正确");
        }
        rescueMapper.updateStatus(id, status);
    }

    public List<Map<String, Object>> listAllAdoptions() {
        return adoptionMapper.listAll();
    }

    public List<Map<String, Object>> listMyAdoptions(Long userId) {
        return adoptionMapper.listByUser(userId);
    }

    public void submitAdoption(Long userId, Map<String, Object> input) {
        Long animalId = longValue(input.get("animalId"));
        String reason = text(input, "reason");
        if (animalId == null || !StringUtils.hasText(reason)) {
            throw new IllegalArgumentException("动物和领养原因不能为空");
        }
        adoptionMapper.insert(userId, animalId, reason);
    }

    public void updateAdoptionStatus(Long id, String status) {
        if (!"PENDING".equals(status) && !"APPROVED".equals(status) && !"REJECTED".equals(status)) {
            throw new IllegalArgumentException("领养状态不正确");
        }
        adoptionMapper.updateStatus(id, status);
    }

    public void deleteAdoption(Long id) {
        adoptionMapper.delete(id);
    }

    public List<Map<String, Object>> listAllFeedback() {
        return feedbackMapper.listAll();
    }

    public List<Map<String, Object>> listMyFeedback(Long userId) {
        return feedbackMapper.listByUser(userId);
    }

    public void submitFeedback(Long userId, String content) {
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("反馈内容不能为空");
        }
        feedbackMapper.insert(userId, content.trim());
    }

    public void replyFeedback(Long id, String reply) {
        if (!StringUtils.hasText(reply)) {
            throw new IllegalArgumentException("回复内容不能为空");
        }
        feedbackMapper.reply(id, reply.trim());
    }

    public void deleteFeedback(Long id) {
        feedbackMapper.delete(id);
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return null;
        }
        return Long.valueOf(String.valueOf(value));
    }
}
