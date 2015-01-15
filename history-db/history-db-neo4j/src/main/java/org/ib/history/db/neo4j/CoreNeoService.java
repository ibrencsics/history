package org.ib.history.db.neo4j;

import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;

import java.util.Iterator;

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
            Node person = savePerson(wikiPerson);
            saveFamily(person, wikiPerson);

            tx.success();

            return person.getId();
        }
    }

    private Node savePerson(WikiPerson wikiPerson) {
        Node person = graphDb.createNode(WikiLabels.PERSON);
        person.setProperty("full", true);
        person.setProperty("wikiPage", wikiPerson.getWikiPage().getLocalPart());
        person.setProperty("name", wikiPerson.getName());
        return person;
    }

    private Node savePerson(WikiNamedResource wikiPerson) {
        Node person = graphDb.createNode(WikiLabels.PERSON);
        person.setProperty("full", false);
        person.setProperty("wikiPage", wikiPerson.getLocalPart());
        person.setProperty("name", wikiPerson.getDisplayText());
        return person;
    }

    private void saveFamily(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getFather()!=null) {
            Node father = savePerson(wikiPerson.getFather());
            baseNode.createRelationshipTo(father, WikiRelationships.HAS_FATHER);
        }
        if (wikiPerson.getMother()!=null) {
            Node mother = savePerson(wikiPerson.getMother());
            baseNode.createRelationshipTo(mother, WikiRelationships.HAS_MOTHER);
        }
    }

    @Override
    public int getPersonsCount() {
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterable<Node> reit =  GlobalGraphOperations.at(graphDb)
                    .getAllNodesWithLabel(WikiLabels.PERSON);

            int count = 0;
            Iterator<Node> iterator = reit.iterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                count++;
            }

            tx.success();

            return count;
        }
    }
}
