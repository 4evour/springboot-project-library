package com.example.agri.controller;

import com.example.agri.common.CurrentUser;
import javax.servlet.http.HttpSession;

public abstract class BaseController {
    protected CurrentUser requireLogin(HttpSession session) {
        CurrentUser user = (CurrentUser) session.getAttribute(AuthController.SESSION_USER);
        if (user == null) {
            throw new IllegalArgumentException("请先登录");
        }
        return user;
    }

    protected CurrentUser requireAdmin(HttpSession session) {
        CurrentUser user = requireLogin(session);
        if (!user.isAdmin()) {
            throw new IllegalArgumentException("需要管理员权限");
        }
        return user;
    }
}
