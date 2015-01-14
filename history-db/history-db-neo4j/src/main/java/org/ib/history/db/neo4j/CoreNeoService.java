package org.ib.history.db.neo4j;

import org.ib.history.wiki.domain.WikiPerson;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class CoreNeoService implements NeoService {

    private final GraphDatabaseService graphDb;

    public CoreNeoService(NeoDb neoDb) {
        this.graphDb = neoDb.getGraphDb();
    }

    public CoreNeoService(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    @Override
    public long save(WikiPerson wikiPerson) {
        try (Transaction tx = graphDb.beginTx()) {
            Node person = graphDb.createNode();
            person.setProperty("wikiPage", wikiPerson.getWikiPage().getLocalPart());
            tx.success();

            return person.getId();
        }
    }
}
