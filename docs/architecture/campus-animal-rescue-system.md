# 校园流浪动物救助系统设计

## 1. 定位

面向校园场景的流浪动物信息浏览、救助上报、领养申请和反馈管理系统。前后端位于同一个 Spring Boot 工程中，适合学习用户端与管理端共用 API 的 SSM 项目结构。

## 2. 分层结构

```text
static/index.html + app.js + style.css
        |
        | axios /api/**
        v
controller
        |
        v
service
        |
        v
mapper (MyBatis annotation SQL)
        |
        v
MySQL
```

- `controller`：REST API、登录态和管理员权限入口。
- `service`：表单校验、状态流转、用户数据隔离和关联数据保护。
- `mapper`：参数绑定的 MyBatis SQL。
- `common`：统一响应、Session 用户对象、密码配置和全局异常处理。
- `static`：Vue 页面、样式、axios 调用和本地前端依赖。

## 3. 数据表

| 表 | 用途 |
| --- | --- |
| `sys_user` | 用户、角色和账号状态 |
| `animal` | 动物基本信息、位置、健康状态和救助状态 |
| `rescue_record` | 用户提交的救助记录 |
| `adoption_application` | 用户提交的领养申请 |
| `notice` | 公告 |
| `feedback` | 用户反馈和管理员回复 |

动物删除受救助记录和领养申请引用保护，避免数据库外键异常直接暴露给页面。

## 4. 主要流程

### 救助上报

1. 用户填写救助标题、地点和描述，可选关联动物。
2. `RecordController` 校验登录态后调用 `RecordService`。
3. 服务层写入 `rescue_record`，初始状态为 `SUBMITTED`。
4. 管理员在救助管理页面更新为 `PROCESSING` 或 `DONE`。

### 领养申请

1. 用户从动物列表选择动物并提交领养原因。
2. 服务层校验动物和原因，写入 `adoption_application`。
3. 管理员审核申请，状态为 `PENDING`、`APPROVED` 或 `REJECTED`。

## 5. 代码入口

- 登录：`AuthController`、`UserService`
- 动物管理：`AnimalController`、`AnimalService`
- 救助、领养和反馈：`RecordController`、`RecordService`
- 前端入口：`src/main/resources/static/index.html`
- 初始化表结构：`src/main/resources/schema.sql`
- 初始化数据：`src/main/resources/data.sql`
