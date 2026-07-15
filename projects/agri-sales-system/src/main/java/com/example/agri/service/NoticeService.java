package com.example.agri.service;

import com.example.agri.mapper.NoticeMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NoticeService {
    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    public List<Map<String, Object>> list() {
        return noticeMapper.list();
    }

    public void save(Map<String, Object> input) {
        String title = text(input, "title");
        String content = text(input, "content");
        if (!StringUtils.hasText(title) || !StringUtils.hasText(content)) {
            throw new IllegalArgumentException("公告标题和内容不能为空");
        }
        Long id = longValue(input.get("id"));
        if (id == null) {
            noticeMapper.insert(title, content);
        } else {
            noticeMapper.update(id, title, content);
        }
    }

    public void delete(Long id) {
        noticeMapper.delete(id);
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
