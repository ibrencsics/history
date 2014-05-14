package org.ib.history.db.neo4j.java;

import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.logging.BufferingLogger;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.HashMap;
import java.util.Map;

public class Neo4jJavaServiceTest {

    @Test
    public void test() {
        GraphDatabaseService db = new TestGraphDatabaseFactory().newImpermanentDatabase();

        ExecutionEngine engine = new ExecutionEngine( db , new BufferingLogger() );

        ExecutionResult result;
        try ( Transaction ignored = db.beginTx() )
        {

            Map<String,String> props = new HashMap<>();
            props.put("name", "England");
            props.put("capital", "London");

            Map<String,Object> params = new HashMap<>();
            params.put("props", props);

            result = engine.execute( "create (england:Country { props })", params );

            ignored.success();

            result = engine.execute("match (n) return n.name, n.capital");
            System.out.println(result.dumpToString());
        }
    }
}
