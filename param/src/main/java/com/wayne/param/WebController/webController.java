package com.wayne.param.WebController;

import com.wayne.param.Person;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
@RestController
public class webController {
    @RequestMapping
    public void savePerson(@Valid Person person, BindingResult result) {
        System.out.println("person: "+ person);
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error: list){
                System.out.println(error.getCode() + "-" + error.getDefaultMessage());
            }
        }
    }
}
