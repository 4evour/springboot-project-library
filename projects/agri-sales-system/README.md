# 智慧农产品销售系统

## 技术栈

- JDK 8
- Spring Boot 2.7.18
- Spring MVC + Spring + MyBatis
- MySQL
- HTML + Vue.js + axios

## 数据库

1. 创建数据库：

```sql
CREATE DATABASE agri_sales_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改 `src/main/resources/application.yml` 中的 MySQL 用户名和密码。
3. 启动项目时会自动执行 `schema.sql` 和 `data.sql`。

## 启动

```bash
mvn spring-boot:run
```

访问：

- 前台和后台入口：`http://localhost:8081`

默认账号：

- 管理员：`admin / 123456`
- 普通用户：`user / 123456`

## 功能

- 用户注册、登录、退出
- 商品分类、商品浏览、购物车、下单、我的订单
- 管理员管理用户、分类、商品、订单、公告

## 验证

在有 JDK 8 和 Maven 的环境中执行：

```bash
mvn test
```
