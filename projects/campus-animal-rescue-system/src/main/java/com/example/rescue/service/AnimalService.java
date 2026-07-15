package com.example.rescue.service;

import com.example.rescue.mapper.AnimalMapper;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AnimalService {
    private final AnimalMapper animalMapper;

    public AnimalService(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }

    public List<Map<String, Object>> list() {
        return animalMapper.list();
    }

    public void save(Map<String, Object> input) {
        String name = text(input, "name");
        String species = text(input, "species");
        if (!StringUtils.hasText(name) || !StringUtils.hasText(species)) {
            throw new IllegalArgumentException("动物名称和种类不能为空");
        }
        Long id = longValue(input.get("id"));
        if (id == null) {
            animalMapper.insert(name, species, text(input, "gender"), text(input, "age"), text(input, "location"), text(input, "healthStatus"), text(input, "description"), text(input, "imageUrl"), textOrDefault(input, "status", "WAITING"));
        } else {
            animalMapper.update(id, name, species, text(input, "gender"), text(input, "age"), text(input, "location"), text(input, "healthStatus"), text(input, "description"), text(input, "imageUrl"), textOrDefault(input, "status", "WAITING"));
        }
    }

    public void delete(Long id) {
        if (animalMapper.countReferences(id) > 0) {
            throw new IllegalArgumentException("动物已有救助或领养记录，不能删除");
        }
        animalMapper.delete(id);
    }

    private String text(Map<String, Object> input, String key) {
        Object value = input.get(key);
        return value == null ? "" : String.valueOf(value).trim();
    }

    private String textOrDefault(Map<String, Object> input, String key, String defaultValue) {
        String value = text(input, key);
        return StringUtils.hasText(value) ? value : defaultValue;
    }

    private Long longValue(Object value) {
        if (value == null || !StringUtils.hasText(String.valueOf(value))) {
            return null;
        }
        return Long.valueOf(String.valueOf(value));
    }
}
