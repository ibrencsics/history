package org.ib.history.db.neo4j.cypher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.db.neo4j.WikiLabels;
import org.ib.history.db.neo4j.WikiProperties;
import org.ib.history.db.neo4j.data.NeoStatistics;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.ib.history.db.neo4j.WikiLabels.*;

public class NeoCypherServiceImpl implements NeoCypherService {

    private static Logger logger = LogManager.getLogger(NeoCypherServiceImpl.class);

    private final GraphDatabaseService graphDb;

    public NeoCypherServiceImpl(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    @Override
    public List<String> getPersonsByPattern(String pattern) {
        List<String> suggestions = new LinkedList<>();

        try (Transaction tx = graphDb.beginTx()) {
            ExecutionEngine engine = new ExecutionEngine(graphDb);

            String cql = "match (p:PERSON) where p.wikiPage=~ '(?i).*" + pattern + ".*' return p";

            ExecutionResult result = engine.execute(cql);

            for (Map<String, Object> row : result) {
                Node node = (Node) row.get("p");
                suggestions.add((String) node.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()));
            }

            tx.success();
        }

        return suggestions;
    }

    @Override
    public NeoStatistics getStatistics() {
        NeoStatistics stat = new NeoStatistics();

        try (Transaction tx = graphDb.beginTx()) {
            ExecutionEngine engine = new ExecutionEngine(graphDb);
            ExecutionResult result;

            stat.setPersonNodeCount(getCountOf(engine, PERSON));
            stat.setCountryNodeCount(getCountOf(engine, COUNTRY));
            stat.setHouseNodeCount(getCountOf(engine, HOUSE));
            stat.setPersonNodeFailureCount(getCountOf(engine, FAILED_PERSON));

            tx.success();
        }

        return stat;
    }

    @Override
    public String execute(String query) {
        if (stopMutation(query)) {
            return "Mutating queries are not allowed";
        }

        try (Transaction tx = graphDb.beginTx()) {
            ExecutionEngine engine = new ExecutionEngine(graphDb);
            ExecutionResult result = engine.execute(query);
            tx.success();

            return result.dumpToString();
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    boolean stopMutation(String query) {
        String lowQuery = query.toLowerCase();
        String[] forbidden = {"create", "insert", "update", "delete"};

//        boolean filter = false;
//
//        Stream.of(forbidden).
//                map(f -> lowQuery.contains(f))
//                .reduce(false, (f1, f2) -> f1 | f2);
//
//        return filter;

        for (String f : forbidden) {
            if (lowQuery.contains(f)) {
                return true;
            }
        }

        return false;
    }

    private Long getCountOf(ExecutionEngine engine, WikiLabels label) {
        ExecutionResult result = engine.execute("match (p:" + label.name() + ") return count(p)");
        return getLongResult(result);
    }

    private Long getLongResult(ExecutionResult result) {
        for (Map<String, Object> row : result) {
            return (Long) row.get("count(p)");
        }

        throw new IllegalArgumentException("No int present in the ExecutionResult");
    }
}
