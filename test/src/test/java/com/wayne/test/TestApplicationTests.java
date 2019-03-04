package com.wayne.test;

import com.wayne.test.controller.HelloController;
import com.wayne.test.services.HelloService;
import com.wayne.test.services.HelloServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Resource
    private HelloService helloService;
    /**
     * OutputCapture 是 Spring Boot 提供的一个测试类，
     * 它能捕获 System.out 和 System.err 的输出，
     * 我们可以利用这个特性来判断程序中的输出是否执行
     */
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void sayHelloTest() {
        helloService.sayHello();
        assertThat(this.outputCapture.toString().contains("hello service")).isTrue();

    }

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/hello")
                .accept(MediaType.APPLICATION_JSON_UTF8))
//                .andDo(print());
                .andExpect(content().string(containsString("hello")));
    }

}
