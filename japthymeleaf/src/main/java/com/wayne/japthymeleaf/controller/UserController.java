package com.wayne.japthymeleaf.controller;

import com.wayne.japthymeleaf.model.User;
import com.wayne.japthymeleaf.param.UserParam;
import com.wayne.japthymeleaf.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/add")
    public String add(@Valid UserParam userParam, BindingResult result, Model model) {
        String errorMsg = "";
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for(ObjectError error : list) {
                errorMsg= errorMsg + error.getCode() + "-" + error.getDefaultMessage() + ";";
            }

            model.addAttribute("errorMsg", errorMsg);
            return "user/userAdd";
        }

        User u = userRepository.findByUserName(userParam.getUserName());
        if (u!=null) {
            model.addAttribute("errorMsg","用户已存在！");
            return "user/userAdd";
        }
        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        user.setRegTime(new Date());
        userRepository.save(user);
        return "redirect:/list";
    }
}
