package org.ib.history.db.neo4j;

public interface Neo4jTemplate {
    <T> T query(String query, Converter<T> converter);
}
