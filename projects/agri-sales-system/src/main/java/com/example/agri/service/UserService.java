package com.example.agri.service;

import com.example.agri.common.CurrentUser;
import com.example.agri.mapper.UserMapper;
import java.util.List;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public CurrentUser login(String username, String password) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new IllegalArgumentException("用户名和密码不能为空");
        }
        Map<String, Object> user = userMapper.findByUsername(username.trim());
        if (user == null || !"ACTIVE".equals(String.valueOf(user.get("status")))) {
            throw new IllegalArgumentException("账号不存在或已禁用");
        }
        if (!passwordEncoder.matches(password, String.valueOf(user.get("password")))) {
            throw new IllegalArgumentException("用户名或密码错误");
        }
        return toCurrentUser(user);
    }

    public void register(String username, String password, String realName, String phone) {
        validateUserInput(username, password, realName);
        if (userMapper.findByUsername(username.trim()) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        userMapper.insert(username.trim(), passwordEncoder.encode(password), realName.trim(), phone, "USER");
    }

    public void ensureDefaults() {
        createIfMissing("admin", "管理员", "ADMIN");
        createIfMissing("user", "普通用户", "USER");
    }

    public List<Map<String, Object>> listUsers() {
        return userMapper.findAll();
    }

    public void updateStatus(Long id, String status) {
        if (!"ACTIVE".equals(status) && !"DISABLED".equals(status)) {
            throw new IllegalArgumentException("用户状态不正确");
        }
        userMapper.updateStatus(id, status);
    }

    public void deleteUser(Long id) {
        userMapper.deleteNonAdmin(id);
    }

    private void createIfMissing(String username, String realName, String role) {
        if (userMapper.findByUsername(username) == null) {
            userMapper.insert(username, passwordEncoder.encode("123456"), realName, "", role);
        }
    }

    private void validateUserInput(String username, String password, String realName) {
        if (!StringUtils.hasText(username) || username.trim().length() < 3) {
            throw new IllegalArgumentException("用户名至少 3 个字符");
        }
        if (!StringUtils.hasText(password) || password.length() < 6) {
            throw new IllegalArgumentException("密码至少 6 个字符");
        }
        if (!StringUtils.hasText(realName)) {
            throw new IllegalArgumentException("姓名不能为空");
        }
    }

    private CurrentUser toCurrentUser(Map<String, Object> user) {
        CurrentUser currentUser = new CurrentUser();
        currentUser.setId(((Number) user.get("id")).longValue());
        currentUser.setUsername(String.valueOf(user.get("username")));
        currentUser.setRealName(String.valueOf(user.get("realName")));
        currentUser.setRole(String.valueOf(user.get("role")));
        return currentUser;
    }
}
