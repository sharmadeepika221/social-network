package com.social.network.userservice.controller;

import com.social.network.oauth2.test.factory.TokenFactory;
import com.social.network.userservice.UserServiceApplication;
import com.social.network.userservice.dto.CreateUserRequest;
import com.social.network.userservice.entity.Role;
import com.social.network.userservice.entity.User;
import com.social.network.userservice.repository.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
@Sql(scripts = "classpath:user/users.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TokenFactory tokenFactory;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void shouldCreateNewUser() throws Exception {

        final var user = new CreateUserRequest();
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setName("Name Name");

        final String json = MAPPER.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .with(tokenFactory.token("ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());

        final User actualUser = userRepository.findByEmail(user.getEmail()).get();
        assertThat(actualUser, notNullValue());
        assertThat(actualUser.getEmail(), equalTo(user.getEmail()));
        assertThat(actualUser.getName(), equalTo(user.getName()));
        assertThat(actualUser.getPassword(), notNullValue());
        assertThat(actualUser.getRole(), equalTo(Role.USER));
    }

    @Test
    public void shouldFailWhenUserIsNotValid() throws Exception {
        final var user = new CreateUserRequest();
        user.setEmail("t");
        user.setPassword("p");
        user.setName("n");

        String json = MAPPER.writeValueAsString(user);

        mockMvc.perform(post("/users")
                .with(tokenFactory.token("ui"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

}