package org.ib.history.db.neo4j;

import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.Map;

public interface Converter<T> {
    T convert(QueryResult<Map<String, Object>> result);
}
