package com.social.network.test.environment;

public interface Environment {
    String baseHost();
    void beforeTests();
    void afterTests();
}
