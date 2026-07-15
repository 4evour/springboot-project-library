# 校园流浪动物救助管理系统

## 技术栈

- JDK 8
- Spring Boot 2.7.18
- Spring MVC + Spring + MyBatis
- MySQL
- HTML + Vue.js + axios

## 数据库

1. 创建数据库：

```sql
CREATE DATABASE campus_animal_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改 `src/main/resources/application.yml` 中的 MySQL 用户名和密码。
3. 启动项目时会自动执行 `schema.sql` 和 `data.sql`。

## 启动

```bash
mvn spring-boot:run
```

访问：

- 前台和后台入口：`http://localhost:8082`

默认账号：

- 管理员：`admin / 123456`
- 普通用户：`user / 123456`

## 功能

- 用户注册、登录、退出
- 动物浏览、救助提交、领养申请、我的记录、公告、反馈
- 管理员管理用户、动物、救助记录、领养申请、公告、反馈

## 验证

在有 JDK 8 和 Maven 的环境中执行：

```bash
mvn test
```
