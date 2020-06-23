package com.social.network.userqueryservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExpandParamsBuilder {
    private static final String EXPAND = "expand";

    private final String[] params;

    public ExpandParamsBuilder(String params) {
        Objects.requireNonNull(params);
        if (params.isEmpty()) {
            this.params = new String[0];
        } else {
            this.params = params.split(",");
        }
    }

    public Map<String, Object> build() {
        var params = new HashMap<String, Object>();

        for (String param : this.params) {
            param = param.trim();
            String key = EXPAND + Character.toUpperCase(param.charAt(0)) + param.substring(1).toLowerCase();
            params.put(key, Boolean.TRUE);
        }

        return params;
    }
}
