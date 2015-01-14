package org.ib.history.db.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class NeoDb {

    private static final String DB_PATH = "/opt/app-data/history-neo4j";
    private GraphDatabaseService graphDb;

    public NeoDb() {
        this(DB_PATH);
    }

    public NeoDb(String dbPath) {
        this.graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
        registerShutdownHook(graphDb);
    }

    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook( new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        } );
    }
}
