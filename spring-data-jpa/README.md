# Spring Data JPA 的基本使用
## 概念

JPA 由来

ORM 框架能够将 Java 对象映射到关系数据库中，能够直接持久化复杂的 Java 对象。ORM 框架的出现，可以让开发者从数据库编程中解脱出来，把更多的精力放在了业务模型与业务逻辑上。目前比较流行的 ORM 框架有 Hibernate、MyBatis、TopLink、Spring JDBC 等。

在 JPA 规范之前，由于没有官方的标准，使得各 ORM 框架之间的 API 差别很大，使用了某种 ORM 框架的系统会严重受制于该 ORM 的标准。基于此，Sun 引入新的 JPA ORM，主要的原因有：其一，简化现有 Java EE 和 Java SE 应用开发工作；其二，Sun 希望整合 ORM 技术，实现统一的 API 调用接口。

JPA 是什么

JPA（Java Persistence API）是 Sun 官方提出的 Java 持久化规范。它为 Java 开发人员提供了一种对象 / 关联映射工具来管理 Java 应用中的关系数据。它的出现主要是为了简化现有的持久化开发工作和整合 ORM 技术，结束现在 Hibernate、TopLink、JDO 等 ORM 框架各自为营的局面。

值得注意的是，JPA 是在充分吸收了现有的 Hibernate、TopLink、JDO 等 ORM 框架的基础上发展而来的，具有易于使用、伸缩性强等优点。从目前的开发社区的反应上看，JPA 受到了极大的支持和赞扬，其中就包括了 Spring 与 EJB 3.0 的开发团队。

> 注意：JPA 是一套规范，不是一套产品，那么像 Hibernate、TopLink、JDO 它们是一套产品，如果说这些产品实现了这个 JPA 规范，那么我们就可以称他们为 JPA 的实现产品。
Spring Data JPA

Spring Data JPA 是 Spring 基于 ORM 框架、JPA 规范的基础上封装的一套 JPA 应用框架，可以让开发者用极简的代码即可实现对数据的访问和操作。它提供了包括增、删、改、查等在内的常用功能，且易于扩展，学习并使用 Spring Data JPA 可以极大提高开发效率。Spring Data JPA 其实就是 Spring 基于 Hibernate 之上构建的 JPA 使用解决方案，方便在 Spring Boot 项目中使用 JPA 技术。

> Spring Data JPA 让我们解脱了 DAO 层的操作，基本上所有 CRUD 都可以依赖于它实现。

# Spring Data JPA 的高级用法
## 自定义 SQL 查询
## 分页查询
Pageable 是 Spring 封装的分页实现类，使用的时候需要传入页数、每页条数和排序规则，Page 是 Spring 封装的分页对象，封装了总页数、分页数据等。返回对象除使用 Page 外，还可以使用 Slice 作为返回值。

Page 和 Slice 的区别如下：
- Page 接口继承自 Slice 接口，而 Slice 继承自 Iterable 接口。
- Page 接口扩展了 Slice 接口，添加了获取总页数和元素总数量的方法，因此，返回 Page 接口时，必须执行两条 SQL，一条复杂查询分页数据，另一条负责统计数据数量。
- 返回 Slice 结果时，查询的 SQL 只会有查询分页数据这一条，不统计数据数量。
- 用途不一样：Slice 不需要知道总页数、总数据量，只需要知道是否有下一页、上一页，是否是首页、尾页等，比如前端滑动加载一页可用；而 Page 知道总页数、总数据量，可以用于展示具体的页数信息，比如后台分页查询。

## 复杂查询
我们可以通过 AND 或者 OR 等连接词来不断拼接属性来构建多条件查询，但如果参数大于 6 个时，方法名就会变得非常的长，并且还不能解决动态多条件查询的场景。到这里就需要给大家介绍另外一个利器 JpaSpecificationExecutor 了。

JpaSpecificationExecutor 是 JPA 2.0 提供的 Criteria API 的使用封装，可以用于动态生成 Query 来满足我们业务中的各种复杂场景。Spring Data JPA 为我们提供了 JpaSpecificationExecutor 接口，只要简单实现 toPredicate 方法就可以实现复杂的查询。

我们来看一下 JpaSpecificationExecutor 的源码：
```$xslt
public interface JpaSpecificationExecutor<T> {
   //根据 Specification 条件查询单个对象，注意的是，如果条件能查出来多个会报错
   T findOne(@Nullable Specification<T> spec);
   //根据 Specification 条件查询 List 结果
   List<T> findAll(@Nullable Specification<T> spec);
   //根据 Specification 条件，分页查询
   Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);
   //根据 Specification 条件，带排序的查询结果
   List<T> findAll(@Nullable Specification<T> spec, Sort sort);
   //根据 Specification 条件，查询数量
   long count(@Nullable Specification<T> spec);
}
```

JpaSpecificationExecutor 的源码很简单，根据 Specification 的查询条件返回 List、Page 或者 count 数据。在使用 JpaSpecificationExecutor 构建复杂查询场景之前，我们需要了解几个概念：

- Root<T> root，代表了可以查询和操作的实体对象的根，开一个通过 get("属性名") 来获取对应的值。
- CriteriaQuery query，代表一个 specific 的顶层查询对象，它包含着查询的各个部分，比如 select 、from、where、group by、order by 等。
- CriteriaBuilder cb，来构建 CritiaQuery 的构建器对象，其实就相当于条件或者是条件组合，并以 Predicate 的形式返回。

## 多表查询
多表查询在 Spring Data JPA 中有两种实现方式，第一种是利用 Hibernate 的级联查询来实现，第二种是创建一个结果集的接口来接收连表查询后的结果，