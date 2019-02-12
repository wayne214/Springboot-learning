# restfulapp
# Spring Boot 构建一个 RESTful Web 服务
## 什么是 RESTful

RESTful 是目前最流行的一种互联网软件架构。REST（Representational State Transfer，表述性状态转移）一词是由 Roy Thomas Fielding 在他 2000 年博士论文中提出的，定义了他对互联网软件的架构原则，如果一个架构符合 REST 原则，则称它为 RESTful 架构。

RESTful 架构一个核心概念是“资源”（Resource）。从 RESTful 的角度看，网络里的任何东西都是资源，它可以是一段文本、一张图片、一首歌曲、一种服务等，每个资源都对应一个特定的 URI（统一资源定位符），并用它进行标示，访问这个 URI 就可以获得这个资源。

资源可以有多种表现形式，也就是资源的“表述”（Representation），比如一张图片可以使用 JPEG 格式也可以使用 PNG 格式。URI 只是代表了资源的实体，并不能代表它的表现形式。

互联网中，客户端和服务端之间的互动传递的就只是资源的表述，我们上网的过程，就是调用资源的 URI，获取它不同表现形式的过程。这种互动只能使用无状态协议 HTTP，也就是说，服务端必须保存所有的状态，客户端可以使用 HTTP 的几个基本操作，包括 GET（获取）、POST（创建）、PUT（更新）与 DELETE（删除），使得服务端上的资源发生“状态转化”（State Transfer），也就是所谓的“表述性状态转移”。

## Spring Boot 对 RESTful 的支持
Spring Boot 全面支持开发 RESTful 程序，通过不同的注解来支持前端的请求，除了经常使用的注解外，Spring Boot 还提了一些组合注解。这些注解来帮助简化常用的 HTTP 方法的映射，并更好地表达被注解方法的语义。

@GetMapping，处理 Get 请求
@PostMapping，处理 Post 请求
@PutMapping，用于更新资源
@DeleteMapping，处理删除请求
@PatchMapping，用于更新部分资源
其实这些组合注解就是我们使用的 @RequestMapping 的简写版本，下面是 Java 类中的使用示例：
```
@GetMapping(value="/xxx")
等价于
@RequestMapping(value = "/xxx",method = RequestMethod.GET)

@PostMapping(value="/xxx")
等价于
@RequestMapping(value = "/xxx",method = RequestMethod.POST)

@PutMapping(value="/xxx")
等价于
@RequestMapping(value = "/xxx",method = RequestMethod.PUT)

@DeleteMapping(value="/xxx")
等价于
@RequestMapping(value = "/xxx",method = RequestMethod.DELETE)

@PatchMapping(value="/xxx")
等价于
@RequestMapping(value = "/xxx",method = RequestMethod.PATCH)

```

通过以上可以看出 RESTful 在请求的类型中就指定了对资源的操控。

## 使用 Swagger 2 构建 RESTful APIs
Swagger 是一系列 RESTful API 的工具，通过 Swagger 可以获得项目的一种交互式文档，客户端 SDK 的自动生成等功能。

Swagger 的目标是为 REST APIs 定义一个标准的、与语言无关的接口，使人和计算机在看不到源码或者看不到文档或者不能通过网络流量检测的情况下，能发现和理解各种服务的功能。当服务通过 Swagger 定义，消费者就能与远程的服务互动通过少量的实现逻辑。类似于低级编程接口，Swagger 去掉了调用服务时的很多猜测。

Swagger（丝袜哥）是世界上最流行的 API 表达工具。

Swagger 是一个简单但功能强大的 API 表达工具。它具有地球上最大的 API 工具生态系统，数以千计的开发人员，使用几乎所有的现代编程语言，都在支持和使用 Swagger。使用 Swagger 生成 API，我们可以得到交互式文档，自动生成代码的 SDK 以及 API 的发现特性等。

使用 Spring Boot 集成 Swagger 的理念是，使用注解来标记出需要在 API 文档中展示的信息，Swagger 会根据项目中标记的注解来生成对应的 API 文档。Swagger 被号称世界上最流行的 API 工具，它提供了 API 管理的全套解决方案，API 文档管理需要考虑的因素基本都包含，这里将讲解最常用的定制内容。
