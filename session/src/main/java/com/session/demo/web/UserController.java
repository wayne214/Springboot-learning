package com.session.demo.web;

import com.session.demo.model.User;
import com.session.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/hello")
    @Cacheable(value = "helloCache")
    public String hello(String name) {
        System.out.println("没有走缓存！");
        return "hello" + name;
    }

    /**
     * @Cacheable 支持如下几个参数:
     * value：缓存的名称。
     * key：缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写；如果不指定，则缺省按照方法的所有参数进行组合。
     * condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持 SpEL。
     * @param name
     * @return
     */
    @RequestMapping("/condition")
    @Cacheable(value="condition",condition="#name.length() <= 4")
    public String condition(String name) {
        System.out.println("没有走缓存！");
        return "hello "+name;
    }

    @RequestMapping(value = "/setSession")
    public Map<String, Object> setSession (HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("message", request.getRequestURL());
        map.put("request Url",request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/getSession")
    public Object getSession (HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("message"));
        return map;
    }

    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request) {
        String msg = "index content";
        Object user = request.getSession().getAttribute("user");
        if (user==null) {
            msg = "please login first!";
        }
        return msg;
    }

    @RequestMapping(value = "/login")
    public String login (HttpServletRequest request, String userName, String password) {
        String msg = "login failure !";
        User user = userRepository.findByUserName(userName);
        if (user !=null && user.getPassword().equals(password)) {
            request.getSession().setAttribute("user", user);
            msg = "login successful!";
        }
        return msg;
    }

    @RequestMapping(value = "/loginout")
    public String loginout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "loginout successful!";
    }
}
