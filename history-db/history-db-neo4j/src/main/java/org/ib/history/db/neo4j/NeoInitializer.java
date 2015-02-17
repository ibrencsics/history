package org.ib.history.db.neo4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NeoInitializer {

    private static Logger logger = LogManager.getLogger(NeoInitializer.class);

    private static final Map<Label,String> INDEXES = new HashMap<>();
    static {
        INDEXES.put(WikiLabels.PERSON, WikiProperties.WIKI_PAGE.getPropertyName());
    }

    private final GraphDatabaseService graphDb;

    public NeoInitializer(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    public void init() {
        logger.info("Initializing the Neo4j DB");

        try (Transaction tx = graphDb.beginTx()) {

            INDEXES.entrySet().stream().forEach(indexEntry -> {

                graphDb.schema().getIndexes(indexEntry.getKey()).forEach(index -> {

                    boolean alreadyIndexed = false;

                    Iterator<String> iter = index.getPropertyKeys().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next();

                        if (key.equals(indexEntry.getValue())) {
                            logger.info("Index for the {} property of nodes with label {} already existing",
                                    indexEntry.getValue(),
                                    indexEntry.getKey());

                            alreadyIndexed = true;
                            break;
                        }
                    };

                    if (!alreadyIndexed) {
                        logger.info("Creating index for the {} property of nodes with label {}",
                                indexEntry.getValue(),
                                indexEntry.getKey());

                        graphDb.schema()
                                .indexFor(indexEntry.getKey())
                                .on(indexEntry.getValue())
                                .create();
                    }
                });
            });

            tx.success();
        }
    }
}
