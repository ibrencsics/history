package org.ib.history.db.neo4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.neo4j.graphdb.*;
import org.neo4j.tooling.GlobalGraphOperations;

import java.util.Iterator;
import java.util.Optional;

public class CoreNeoService implements NeoService {

    private static Logger logger = LogManager.getLogger(CoreNeoService.class);

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

    private Node savePerson(WikiPerson wikiPerson) {
        String wikiPage = wikiPerson.getWikiPage().getLocalPart();
        Optional<Node> maybePerson = getNodeByWikiPage(wikiPage);

        if (!maybePerson.isPresent()) {
            logger.debug("Node [{}] not existing, creating now", wikiPage);

            Node person = graphDb.createNode(WikiLabels.PERSON);
            person.setProperty(WikiProperties.FULL.getPropertyName(), true);
            person.setProperty(WikiProperties.WIKI_PAGE.getPropertyName(), wikiPage);
            person.setProperty(WikiProperties.NAME.getPropertyName(), wikiPerson.getName());
            return person;
        } else {
            Node person = maybePerson.get();

            if (person.getProperty(WikiProperties.FULL.getPropertyName()).equals(false)) {
                logger.debug("Node [{}] already existing but non-full", wikiPage);
                // TODO: save details -> full
            } {
                logger.debug("Node [{}] already existing and full", wikiPage);
            }

            return maybePerson.get();
        }
    }

    private Node savePerson(WikiNamedResource wikiPerson) {
        String wikiPage = wikiPerson.getLocalPart();
        Optional<Node> maybePerson = getNodeByWikiPage(wikiPage);

        if (!maybePerson.isPresent()) {
            logger.debug("Node [{}] not existing, creating now", wikiPage);

            Node person = graphDb.createNode(WikiLabels.PERSON);
            person.setProperty(WikiProperties.FULL.getPropertyName(), false);
            person.setProperty(WikiProperties.WIKI_PAGE.getPropertyName(), wikiPage);
            person.setProperty(WikiProperties.NAME.getPropertyName(), wikiPerson.getDisplayText());

            return person;
        } else {
            logger.debug("Node [{}] already existing", wikiPage);
            return maybePerson.get();
        }
    }

    private Optional<Node> getNodeByWikiPage(String wikiPage) {
        ResourceIterable<Node> existingNodes = graphDb.findNodesByLabelAndProperty(
                WikiLabels.PERSON, WikiProperties.WIKI_PAGE.getPropertyName(), wikiPage);
        ResourceIterator<Node> iterator = existingNodes.iterator();

        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }
}
