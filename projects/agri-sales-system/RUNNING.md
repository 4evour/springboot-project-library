# 智慧农产品销售系统操作手册

## 1. 项目说明

本项目是一个前后端放在同一个 Spring Boot 工程中的智慧农产品销售系统。后端使用 Spring Boot、Spring MVC、Spring、MyBatis 和 MySQL，前端使用 HTML、Vue.js 和 axios。

## 2. 环境要求

- JDK 8
- Maven 3.6 或更高版本
- MySQL 8.x，MySQL 5.7 通常也可以运行
- 浏览器：Chrome、Edge 或 Firefox

确认环境是否可用：

```bash
java -version
mvn -version
mysql --version
```

## 3. 数据库准备

1. 登录 MySQL：

```bash
mysql -u root -p
```

2. 创建数据库：

```sql
CREATE DATABASE agri_sales_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 确认 `src/main/resources/application.yml` 中的数据库配置与本机一致：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/agri_sales_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root
```

如果你的 MySQL 用户名或密码不是 `root/root`，只需要修改 `username` 和 `password`。

项目启动时会自动执行：

- `src/main/resources/schema.sql`：创建 5 张业务表
- `src/main/resources/data.sql`：写入默认账号、分类、商品和公告数据

## 4. 启动项目

进入项目根目录，也就是包含 `pom.xml` 的目录，执行：

```bash
mvn spring-boot:run
```

启动成功后访问：

```text
http://localhost:8081
```

## 5. 默认账号

管理员账号：

```text
admin / 123456
```

普通用户账号：

```text
user / 123456
```

## 6. 功能复现流程

普通用户流程：

1. 使用 `user / 123456` 登录。
2. 浏览商品列表。
3. 将商品加入购物车。
4. 进入购物车提交订单。
5. 在“我的订单”中查看订单状态。

管理员流程：

1. 使用 `admin / 123456` 登录。
2. 管理用户、分类、商品、订单和公告。
3. 修改商品或订单后，刷新前台页面查看展示效果。

## 7. 测试命令

在项目根目录执行：

```bash
mvn test
```

如果看到 `BUILD SUCCESS`，说明项目可以正常编译并通过现有测试。

## 8. 常见问题

### 端口 8081 被占用

修改 `src/main/resources/application.yml`：

```yaml
server:
  port: 8081
```

把 `8081` 改成未占用端口，例如 `8091`。

### 数据库连接失败

检查三项：

- MySQL 服务是否已经启动
- 数据库 `agri_sales_db` 是否已经创建
- `application.yml` 中的用户名和密码是否正确

### 页面没有默认数据

确认 `application.yml` 中保留了 SQL 初始化配置：

```yaml
spring:
  sql:
    init:
      mode: always
```
