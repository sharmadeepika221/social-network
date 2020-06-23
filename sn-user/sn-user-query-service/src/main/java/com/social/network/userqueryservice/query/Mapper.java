package com.social.network.userqueryservice.query;

import java.util.List;
import java.util.Map;

public interface Mapper <ENTITY> {
    ENTITY getSingle(Map<String, Object> params);
    List<ENTITY> getList(Map<String, Object> params);
}
