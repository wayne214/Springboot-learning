package com.wayne.param;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * |---get方式Url传参
 *         |---@PathVariable 即：url/id/1994 形式
 *         |---@RequestParam 即：url?username=zed形式
 *     |---POST方式传参
 *         |---@RequestParam
 *         |---请求体中加入文本
 */
@RestController
public class IndexController {
    /**
     * pathVriable路径url传参
     * 获取路径参数。即url/{id}这种形式。
     * @param name
     * @return
     */
    @GetMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return "hello " + name;
    }

    /**
     * 获取查询参数。即url?name=这种形式
     * @param name
     * @return
     */
    @GetMapping("/hello")
    public String index2(@RequestParam("name") String name) {
        return "hello2 " + name;
    }

    @GetMapping("/hello3/{id}")
    public String index2(@PathVariable("id") String id, @RequestParam("name")String name) {
        id = StringUtils.trimAllWhitespace(id);
        return "hello3 " + id + "-" + name;
    }

    /**
     * 传递字符串，通过postman测试
     * @param name
     * @param age
     * @return
     */
    @PostMapping("/user")
    public String body(@RequestParam("name")String name, @RequestParam("age") String age) {
        return "name:" + name + "\nage:" + age;
    }

    /**
     * 传递对象
     * @param person
     * @return
     */
    @PostMapping("/person")
    public String person(@RequestBody Person person) {
        return person.toString();
    }
}
