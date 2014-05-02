package org.ib.history.db.neo4j.rest;

import org.ib.history.db.neo4j.Converter;
import org.ib.history.db.neo4j.Neo4jTemplate;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.HashMap;
import java.util.Map;

public class Neo4jRestTemplate implements Neo4jTemplate<QueryResult<Map<String,Object>>> {

    private RestGraphDatabase db;
    private RestCypherQueryEngine engine;

    public Neo4jRestTemplate() {
        db = new RestGraphDatabase("http://localhost:7474/db/data");
        engine = new RestCypherQueryEngine(db.getRestAPI());
    }

    @Override
    public <T> T executeQuery(Converter<T, QueryResult<Map<String, Object>>> converter, String query, String... params) {
        QueryResult<Map<String, Object>> result;
        try ( Transaction tx = db.beginTx() ) {
            result = engine.query(query, new HashMap<String, Object>());
            T domain = converter.convert(result);
            tx.success();
            return domain;
        }
    }

    @Override
    public void executeUpdate(String query, String... params) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
