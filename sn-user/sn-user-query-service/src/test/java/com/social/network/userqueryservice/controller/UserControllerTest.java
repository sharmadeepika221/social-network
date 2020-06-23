package com.social.network.userqueryservice.controller;

import io.github.yashchenkon.test.JsonContentVerifier;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.social.network.oauth2.test.factory.TokenFactory;
import com.social.network.userqueryservice.UserQueryServiceApplication;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserQueryServiceApplication.class)
@Sql(scripts = "classpath:user/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TokenFactory tokenFactory;

    @Rule
    public JsonContentVerifier jsonContentVerifier = new JsonContentVerifier();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    public void testGetUserWithoutFriendsWithoutExpandParam() throws Exception {
        final String responseBody = mockMvc.perform(get("/users/1")
                .with(tokenFactory.token("1", "ui")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }


    @Test
    public void testGetUserWhichDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/1000")
                .with(tokenFactory.token("1", "ui")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserWithFriendRequests() throws Exception {
        final String responseBody = mockMvc.perform(get("/users/5")
                .with(tokenFactory.token("1", "ui")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        jsonContentVerifier.assertJson(responseBody);
    }
}
