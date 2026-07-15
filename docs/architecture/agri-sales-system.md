# 智慧农产品销售系统设计

## 1. 定位

面向校园合作基地的农产品浏览、购物车、下单和后台管理系统。前后端位于同一个 Spring Boot 工程中，适合学习典型的 SSM 分层结构和 Session 登录流程。

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
- `service`：业务校验、订单计算、库存扣减和数据关联保护。
- `mapper`：参数绑定的 MyBatis SQL。
- `common`：统一响应、Session 用户对象、密码配置和全局异常处理。
- `static`：Vue 页面、样式、axios 调用和本地前端依赖。

## 3. 数据表

| 表 | 用途 |
| --- | --- |
| `sys_user` | 用户、角色和账号状态 |
| `product_category` | 商品分类 |
| `product` | 商品、价格、库存和销售状态 |
| `cart_item` | 用户购物车 |
| `orders` | 订单主表 |
| `order_item` | 订单商品明细 |
| `notice` | 公告 |

商品删除受购物车和订单明细引用保护，分类删除受商品引用保护，避免数据库外键异常直接暴露给页面。

## 4. 主要流程

### 登录

1. 前端提交用户名和密码。
2. `AuthController` 调用 `UserService`。
3. 服务层使用 BCrypt 校验密码和账号状态。
4. 用户对象写入 HTTP Session。

### 下单

1. 用户将商品加入购物车。
2. 提交收货信息后读取当前用户购物车。
3. 服务层计算总价并创建订单。
4. 使用带库存条件的 SQL 扣减库存。
5. 写入订单明细并清空购物车。

## 5. 代码入口

- 登录：`AuthController`、`UserService`
- 商品和分类：`ProductController`、`ProductService`
- 购物车和订单：`CartOrderController`、`CartOrderService`
- 前端入口：`src/main/resources/static/index.html`
- 初始化表结构：`src/main/resources/schema.sql`
- 初始化数据：`src/main/resources/data.sql`
