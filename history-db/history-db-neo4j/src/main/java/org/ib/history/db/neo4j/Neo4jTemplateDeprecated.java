package org.ib.history.db.neo4j;

public interface Neo4jTemplateDeprecated<S> {
    <T> T executeQuery(Converter<T, S> converter, String query, String... params);
    void executeUpdate(String query, String... params);
}
