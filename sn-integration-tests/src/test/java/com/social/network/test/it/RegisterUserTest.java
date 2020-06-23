package com.social.network.test.it;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.social.network.test.BaseTest;
import com.social.network.test.api.RegisterUserRequest;

import static com.jayway.restassured.RestAssured.given;
public class RegisterUserTest extends BaseTest {

    @Test
    public void shouldRegisterUser() {
        final String email = UUID.randomUUID().toString() + "@gmail.com";
        final RegisterUserRequest request = new RegisterUserRequest(
                email, UUID.randomUUID().toString(), UUID.randomUUID().toString()

        );

        given()
                .body(request)
                .post(baseHost() + "/sn-user-service/api/users")
                .then()
                .statusCode(200);
    }
}
