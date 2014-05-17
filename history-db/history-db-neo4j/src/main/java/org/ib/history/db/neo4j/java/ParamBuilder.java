package org.ib.history.db.neo4j.java;

import java.util.HashMap;
import java.util.Map;

public class ParamBuilder {

    private Map<String,Object> params = new HashMap<>();

    public ParamBuilder addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public ParamBuilder addParam(String prop, String key, Object value) {
        if (params.get(prop) == null) {
            params.put(prop, new HashMap<String,Object>());
        }
        ((Map<String,Object>)params.get(prop)).put(key, value);
        return this;
    }

    public Map<String,Object> build() {
        return params;
    }
}
