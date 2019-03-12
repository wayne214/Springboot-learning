package com.wayne.param;

import com.wayne.param.WebController.webController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParamApplicationTests {

    @Test
    public void contextLoads() {
    }

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new webController()).build();
    }

    @Test
    public void savePerson() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/savePerson")
        .param("name","")
        .param("age", "666"));
    }
}
