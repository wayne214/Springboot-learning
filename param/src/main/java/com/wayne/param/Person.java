package com.wayne.param;

import javafx.scene.chart.ValueAxis;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Spring Boot 的参数校验依赖于 hibernate-validator 来进行,
 * 使用 Hibernate Validator 校验数据，需要定义一个接收的数据模型，使用注解的形式描述字段校验的规则
 */
public class Person {
    @NotEmpty(message = "姓名不能为空")
    String name;
    @Max(value = 100, message = "年龄不能大于100岁")
    @Min(value = 18, message = "必须年满18岁")
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
