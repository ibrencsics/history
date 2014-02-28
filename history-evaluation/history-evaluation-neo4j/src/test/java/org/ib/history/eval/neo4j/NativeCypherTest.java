package org.ib.history.eval.neo4j;

import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.logging.BufferingLogger;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NativeCypherTest {

    @Test
    public void test() {
        RestGraphDatabase db = new RestGraphDatabase("http://localhost:7474/db/data");

        RestCypherQueryEngine engine = new RestCypherQueryEngine(db.getRestAPI());

        QueryResult<Map<String, Object>> result;
        try ( Transaction tx = db.beginTx() ) {
            result = engine.query("match (n) return n", new HashMap<String, Object>());

            Iterator<Map<String, Object>> iter = result.iterator();
            while (iter.hasNext()) {
                Map<String,Object> map = iter.next();

                for (Map.Entry<String,Object> entry : map.entrySet()) {
                    System.out.println(entry.getKey() +  " : " + entry.getValue());

                    RestNode restNode = (RestNode) entry.getValue();
                    Iterable<String> props = restNode.getPropertyKeys();
                    Iterator<String> it = props.iterator();
                    while (it.hasNext()) {
                        String prop = it.next();
                        System.out.println("  " + prop + " : " + restNode.getProperty(prop));
                    }
                }
            }
            tx.success();
        }
    }
}
