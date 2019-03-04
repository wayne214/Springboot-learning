package com.wayne.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/content")
    public String content() {
        return "content";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    // 只有拥有ADMIN角色的用户才可以访问方法
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }
}
