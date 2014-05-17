package org.ib.history.db.neo4j.java;

import org.ib.history.db.neo4j.Converter;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.logging.BufferingLogger;

import java.util.Map;

public class Neo4jJavaTemplate {

    private final GraphDatabaseService graphDatabaseService;
    private final ExecutionEngine executionEngine;

    public Neo4jJavaTemplate(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
        this.executionEngine = new ExecutionEngine(graphDatabaseService, new BufferingLogger());
    }

    public void executeUpdate(String query, Map<String,Object> parameters) {
        Transaction tx = graphDatabaseService.beginTx();
        try {
            ExecutionResult result = executionEngine.execute(query, parameters);
            tx.success();
        } finally {
            tx.finish();
        }
    }

    public <T> T executeQuery(Converter<T,ExecutionResult> converter, String query) {
        Transaction tx = graphDatabaseService.beginTx();
        try {
            ExecutionResult result = executionEngine.execute(query);
            tx.success();
            return converter.convert(result);
        } finally {
            tx.finish();
        }
    }

    public <T> T executeQuery(Converter<T,ExecutionResult> converter, String query, Map<String,Object> parameters) {
        Transaction tx = graphDatabaseService.beginTx();
        try {
            ExecutionResult result = executionEngine.execute(query, parameters);
            tx.success();
            return converter.convert(result);
        } finally {
            tx.finish();
        }
    }


}
