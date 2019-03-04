package com.wayne.test;

import com.wayne.test.controller.HelloController;
import com.wayne.test.services.HelloService;
import com.wayne.test.services.HelloServiceImpl;
import com.wayne.test.utils.Calculation;
import org.junit.*;
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
import static org.junit.Assert.assertEquals;
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

    //JUnit使用
    Calculation calculation = new Calculation();
    int result;     //测试结果
    //在 JUnit 4 中使用 @Test 标注为测试方法
    @Test
    public void testAdd() {
        System.out.println("-----testAdd开始测试-----");
        result = calculation.add(1,3);
        assertEquals(4, result);
        System.out.println("-----testAdd正常运行结束----");
    }


    //又一个测试方法
    //timeout 表示测试允许的执行时间毫秒数，expected 表示忽略哪些抛出的异常（不会因为该异常导致测试不通过）
    @Test(timeout = 1, expected = NullPointerException.class)
    public void testSub() {
        System.out.println("---testSub开始测试---");

        result = calculation.sub(3, 2);
        assertEquals(1, result);

        throw new NullPointerException();

        //System.out.println("---testSub正常运行结束---");
    }

    //指示该[静态方法]将在该类的[所有]测试方法执行之[前]执行
    @BeforeClass
    public static void beforeAll() {
        System.out.println("||==BeforeClass==||");
        System.out.println("||==通常在这个方法中加载资源==||");
    }

    //指示该[静态方法]将在该类的[所有]测试方法执行之[后]执行
    @AfterClass
    public static void afterAll() {
        System.out.println("||==AfterClass==||");
        System.out.println("||==通常在这个方法中释放资源==||");
    }

    //该[成员方法]在[每个]测试方法执行之[前]执行
    @Before
    public void beforeEvery() {
        System.out.println("|==Before==|");
    }

    //该[成员方法]在[每个]测试方法执行之[后]执行
    @After
    public void afterEvery() {
        System.out.println("|==After==|");
    }

}
