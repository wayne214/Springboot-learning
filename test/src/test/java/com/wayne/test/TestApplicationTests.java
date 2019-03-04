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


    /**
     * @Test，把一个方法标记为测试方法
     * @Before，每一个测试方法执行前自动调用一次
     * @After，每一个测试方法执行完自动调用一次
     * @BeforeClass，所有测试方法执行前执行一次，在测试类还没有实例化就已经被加载，因此用 static 修饰
     * @AfterClass，所有测试方法执行前执行一次，在测试类还没有实例化就已经被加载，因此用 static 修饰
     * @Ignore，暂不执行该测试方法
     * @RunWith 当一个类用 @RunWith 注释或继承一个用 @RunWith 注释的类时，
     * JUnit 将调用它所引用的类来运行该类中的测试而不是开发者再去 JUnit 内部去构建它。
     * 我们在开发过程中使用这个特性看看。
     */


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
