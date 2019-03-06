该系统包括以下功能：管理员注册、注册验证、管理员登录、管理员退出、添加用户、修改用户、删除用户、浏览用户信息等功能。

技术选型，使用 MongoDB 存储系统数据、使用 Filter 检查用户的登录状态、使用 Redis 管理用户 Session、数据缓存、使用 Spring Boot Mail 验证用户注册邮箱，使用 Hibernate-validator 做参数校验，使用 BootStrap 前端框架、Thymeleaf 模板，并且使用 Thymeleaf 进行页面布局。