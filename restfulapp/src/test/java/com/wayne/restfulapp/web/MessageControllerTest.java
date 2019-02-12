package com.wayne.restfulapp.web;

import com.wayne.restfulapp.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

/**
 * @SpringBootTest 注解是 SpringBoot 自 1.4.0 版本开始引入的一个用于测试的注解
 * @RunWith(SpringRunner.class) 代表运行一个 Spring 容器
 * @Before 代表在测试启动时候需要提前加载的内容，这里是提前加载 MVC 环境
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        saveMessages();

    }

    /**
     * 保存一条消息
     * @throws Exception
     */
    @Test
    public void saveMessage() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("text", "text");
        params.add("summary", "summary");

        String mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/message").params(params)).andReturn().getResponse().getContentAsString();
        System.out.println("result=="+mvcResult);

    }

    /**
     * 获取所有消息
     * @throws Exception
     */
    @Test
    public void getAllMessage() throws Exception {
        String mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/messages")).andReturn().getResponse().getContentAsString();
        System.out.println("所有消息=="+mvcResult);
    }

    /**
     * 获取单个消息
     * @throws Exception
     */
    @Test
    public void getMessage() throws Exception {
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/message/6")).andReturn().getResponse().getContentAsString();
        System.out.println("获取单个消息=="+mvcResult);
    }

    /**
     * 修改消息
     * @throws Exception
     */
    @Test
    public void modifyMessage()  throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "6");
        params.add("text", "textss");
        params.add("summary", "summary");
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/message").params(params)).andReturn().getResponse().getContentAsString();
        System.out.println("修改消息=="+mvcResult);
    }

    /**
     * 更新局部字段
     * @throws Exception
     */
    @Test
    public void patchMessage() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("text","wayne214");
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch("/message/text").params(params)).andReturn().getResponse().getContentAsString();
        System.out.println("更新局部字段"+mvcResult);
    }

    /**
     *
     * 删除消息
     * @throws Exception
     */
    @Test
    public void deleteMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/message/6")).andReturn();
        String mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/messages")).andReturn().getResponse().getContentAsString();
        System.out.println("删除消息=="+mvcResult);
    }
    /**
     * 批量添加消息
     */
    private void saveMessages() {
        for (int i = 1; i < 10; i++) {
            final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("text", "text" + i);
            params.add("summary", "summary"+i);

            try {
                MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/message").params(params)).andReturn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
