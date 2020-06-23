package com.social.network.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.social.network.test.environment.Environment;
import com.social.network.test.environment.EnvironmentFactory;

public class BaseTest {

    private static final EnvironmentFactory ENVIRONMENT_FACTORY = new EnvironmentFactory();
    private static final Environment ENVIRONMENT = ENVIRONMENT_FACTORY.build();

    public static String baseHost() {
        return ENVIRONMENT.baseHost();
    }

    @BeforeAll
    public static void beforeAll() {
        ENVIRONMENT.beforeTests();
    }

    @AfterAll
    public static void afterAll() {
        ENVIRONMENT.afterTests();
    }
}
