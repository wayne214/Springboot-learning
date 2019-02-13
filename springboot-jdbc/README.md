# Spring Boot 使用 JDBC 操作数据库
JDBC（Java Data Base Connectivity，Java 数据库连接）是一种用于执行 SQL 语句的 Java API，可以为多种关系数据库提供统一访问，它由一组用 Java 语言编写的类和接口组成。JDBC 提供了一种基准，据此可以构建更高级的工具和接口，使数据库开发人员能够编写数据库应用程序。

说白了 JDBC 就是一套 Java 访问数据库的 API 规范，利用这套规范屏蔽了各种数据库 API 调用的差异性。当 Java 程序需要访问数据库时，直接调用 JDBC API 相关代码进行操作，JDBC 调用各类数据库的驱动包进行交互，最后数据库驱动包和对应的数据库通讯，完成 Java 程序操作数据库。

直接在 Java 程序中使用 JDBC 比较复杂，需要 7 步才能完成数据库的操作：

- 加载数据库驱动
- 建立数据库连接
- 创建数据库操作对象
- 定义操作的 SQL 语句
- 执行数据库操作
- 获取并操作结果集
- 关闭对象，回收资源
关键代码如下：
```
try {
    // 1、加载数据库驱动
    Class.forName(driver);
    // 2、获取数据库连接
    conn = DriverManager.getConnection(url, username, password);
    // 3、获取数据库操作对象
    stmt = conn.createStatement();
    // 4、定义操作的 SQL 语句
    String sql = "select * from user where id = 6";
    // 5、执行数据库操作
    rs = stmt.executeQuery(sql);
    // 6、获取并操作结果集
    while (rs.next()) {
    // 解析结果集
    }
} catch (Exception e) {
    // 日志信息
} finally {
    // 7、关闭资源
}
```
通过上面的示例可以看出直接使用 JDBC 来操作数据库比较复杂，因此后期在 JDBC 的基础上又发展出了很多著名的 ORM 框架，其中最为流行的是 Hibernate、MyBatis 和 Spring JDBC。这三个流行的 ORM 框架在后续的课程中都会讲到，这里主要了解一下 Spring JDBC 在 Spring Boot 中的使用。

Spring Boot 针对 JDBC 的使用提供了对应的 Starter 包：spring-boot-starter-jdbc，它其实就是在 Spring JDBC 上做了进一步的封装，方便在 Spring Boot 生态中更好的使用 JDBC，下面进行示例演示。