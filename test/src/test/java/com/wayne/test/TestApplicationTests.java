package com.wayne.test;

import com.wayne.test.services.HelloService;
import com.wayne.test.services.HelloServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    private HelloService helloService;

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void sayHelloTest() {
        helloService.sayHello();
        assertThat(this.outputCapture.toString().contains("hello service")).isTrue();

    }

}
