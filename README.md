# Spring Boot Project Library

一个用于源码查看、运行复现和学习参考的 Spring Boot 项目库。

仓库中的项目保持独立运行，每个项目都有自己的 `pom.xml`、数据库脚本、前端页面和测试，便于单独阅读、启动和修改。

## 项目索引

| 项目 | 主题 | 默认端口 | 数据库 |
| --- | --- | --- | --- |
| [智慧农产品销售系统](projects/agri-sales-system) | 商品、购物车、订单和后台管理 | `8081` | `agri_sales_db` |
| [校园流浪动物救助系统](projects/campus-animal-rescue-system) | 动物信息、救助、领养和反馈管理 | `8082` | `campus_animal_db` |

## 技术栈

- JDK 8
- Spring Boot 2.7.18
- Spring MVC
- MyBatis
- MySQL
- Maven
- HTML、CSS、Vue.js、axios

前端页面和后端 API 位于同一个 Spring Boot 项目中，Vue 和 axios 已放在项目静态资源目录，默认运行不依赖外部 CDN。

## 快速开始

进入任一项目目录，先创建对应数据库，再执行：

```bash
mvn test
mvn spring-boot:run
```

默认账号：

```text
管理员：admin / 123456
普通用户：user / 123456
```

更完整的数据库配置和操作步骤见各项目的 `README.md` 与 `RUNNING.md`。

## 设计文档

- [农产品销售系统设计](docs/architecture/agri-sales-system.md)
- [校园动物救助系统设计](docs/architecture/campus-animal-rescue-system.md)

## 学习顺序

1. 先阅读项目 README 和系统设计文档，了解业务边界与表结构。
2. 从 `controller` 查看接口入口。
3. 从 `service` 查看业务校验、登录态和权限判断。
4. 从 `mapper` 查看 MyBatis SQL 与数据库关系。
5. 从 `src/main/resources/static` 查看前端页面和 API 调用。
6. 修改代码后执行 `mvn test`，再启动项目进行页面验证。

## 仓库约定

- 新项目放入 `projects/`，保持独立的 Maven 工程。
- 通用设计说明放入 `docs/architecture/`。
- 不提交 `target`、IDE 配置、运行日志、演示视频和本机环境文件。
