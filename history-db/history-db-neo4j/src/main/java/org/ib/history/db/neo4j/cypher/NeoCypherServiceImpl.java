package org.ib.history.db.neo4j.cypher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.List;
import java.util.Map;

public class NeoCypherServiceImpl implements NeoCypherService {

    private static Logger logger = LogManager.getLogger(NeoCypherServiceImpl.class);

    private final GraphDatabaseService graphDb;

    public NeoCypherServiceImpl(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    @Override
    public List<String> getPersonsByPattern(String pattern) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);

        String cql = "match (n:PERSON) where n.name=~ '(?i).*" + pattern + ".*' return n";

        ExecutionResult result = engine.execute(cql);

        System.out.println("Execution result:" + result.toString());

        for(Map<String,Object> row : result){
            System.out.println("Row:" + row);
        }

        return null;
    }
}
