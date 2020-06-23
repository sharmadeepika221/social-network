package com.social.network.test.environment;

import com.social.network.test.api.Properties;

public class EnvironmentFactory {

    public Environment build() {
        final String envType = System.getProperty(Properties.ENV_TYPE);
        if (envType == null) {
            throw new RuntimeException("Environment type is incorrect.");
        }

        switch (envType) {
            case Properties.ENV_TYPE_DOCKER:
                return new DockerEnvironment();
            default:
                throw new RuntimeException("Environment type is incorrect.");
        }
    }
}
