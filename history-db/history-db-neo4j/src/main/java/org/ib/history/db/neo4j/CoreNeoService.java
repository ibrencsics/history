package org.ib.history.db.neo4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.commons.utils.Neo4jDateFormat;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.*;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.tooling.GlobalGraphOperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
        logger.debug("save([{}])", wikiPerson.getName());

        try (Transaction tx = graphDb.beginTx()) {
            Tuple2<Node,Boolean> person = savePerson(wikiPerson);
            saveFamily(person.element1(), wikiPerson);
            saveSpouses(person.element1(), wikiPerson);
            saveIssues(person.element1(), wikiPerson);
            saveHouses(person.element1(), wikiPerson);
            tx.success();

            return person.element1().getId();
        }
    }

    @Override
    public int countOf(Label label) {
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterable<Node> reit =  GlobalGraphOperations.at(graphDb)
                    .getAllNodesWithLabel(label);

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

    @Override
    public Optional<WikiPerson> getPerson(String wikiPage) {
        logger.debug("getPerson([{}])", wikiPage);

        wikiPage = formatWikiPage(wikiPage);

        try (Transaction tx = graphDb.beginTx()) {
            Optional<Node> maybePerson = getNodeByWikiPage(wikiPage, WikiLabels.PERSON);

            if (maybePerson.isPresent() &&
                    maybePerson.get().getProperty(WikiProperties.STATUS.getPropertyName()).equals(NodeStatus.FULL.name())) {

                logger.debug("Page [{}] found in cache", wikiPage);

                Node person = maybePerson.get();

                WikiPerson.Builder builder = new WikiPerson.Builder()
                        .wikiPage(WikiResource.fromLocalPart(wikiPage))
                        .name((String) person.getProperty(WikiProperties.NAME.getPropertyName()))
                        .dateOfBirth(Neo4jDateFormat.parse((String) person.getProperty(WikiProperties.DATE_OF_BIRTH.getPropertyName())))
                        .dateOfDeath(Neo4jDateFormat.parse((String) person.getProperty(WikiProperties.DATE_OF_DEATH.getPropertyName())));

                List<WikiNamedResource> resources;

                resources = getRelations(person, WikiRelationships.HAS_FATHER);
                if (!resources.isEmpty()) {
                    builder.father(resources.get(0));
                }

                resources = getRelations(person, WikiRelationships.HAS_MOTHER);
                if (!resources.isEmpty()) {
                    builder.mother(resources.get(0));
                }

                builder.spouse(getRelations(person, WikiRelationships.IS_SPOUSE_OF));
                builder.issue(getRelations(person, WikiRelationships.HAS_ISSUE));
                builder.house(getRelations(person, WikiRelationships.IN_HOUSE));

                return Optional.of(builder.build());
            } else {
                logger.debug("Page [{}] not found in cache", wikiPage);
                return Optional.empty();
            }
        }
    }

    private String formatWikiPage(String wikiPage) {
        return wikiPage.replace("_", " ");
    }

    private void saveFamily(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getFather()!=null) {
            Tuple2<Node,Boolean> father = saveBase(wikiPerson.getFather(), WikiLabels.PERSON);

            setRelationIfEmpty(baseNode, father.element1(), WikiRelationships.HAS_FATHER);
            setRelationIfEmpty(father.element1(), baseNode, WikiRelationships.HAS_ISSUE);
            setPropertyIfEmpty(father.element1(), WikiProperties.GENDER, GenderType.M.name());
        }
        if (wikiPerson.getMother()!=null) {
            Tuple2<Node,Boolean> mother = saveBase(wikiPerson.getMother(), WikiLabels.PERSON);

            setRelationIfEmpty(baseNode, mother.element1(), WikiRelationships.HAS_MOTHER);
            setRelationIfEmpty(mother.element1(), baseNode, WikiRelationships.HAS_ISSUE);
            setPropertyIfEmpty(mother.element1(), WikiProperties.GENDER, GenderType.F.name());
        }
    }

    private void saveSpouses(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getSpouses()!=null) {
            wikiPerson.getSpouses().stream().forEach(s -> {
                Tuple2<Node,Boolean> spouse = saveBase(s, WikiLabels.PERSON);
                if (spouse.element2()) {
                    baseNode.createRelationshipTo(spouse.element1(), WikiRelationships.IS_SPOUSE_OF);
                    spouse.element1().createRelationshipTo(baseNode, WikiRelationships.IS_SPOUSE_OF);
                }
            });
        }
    }

    private void saveIssues(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getIssues()!=null) {
            wikiPerson.getIssues().stream().forEach(i -> {
                Tuple2<Node,Boolean> issue = saveBase(i, WikiLabels.PERSON);
                if (issue.element2()) {
                    baseNode.createRelationshipTo(issue.element1(), WikiRelationships.HAS_ISSUE);
                }
            });
        }
    }

    private void saveHouses(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getHouses()!=null) {
            wikiPerson.getHouses().stream().forEach(h -> {
                Tuple2<Node,Boolean> house = saveHouse(h);

                setRelationIfEmpty(baseNode, house.element1(), WikiRelationships.IN_HOUSE);
            });
        }
    }

    private Tuple2<Node,Boolean> savePerson(WikiPerson wikiPerson) {
        Tuple2<Node,Boolean> person = saveBase(wikiPerson.getWikiNamedResource(), WikiLabels.PERSON);
        String wikiPage = wikiPerson.getWikiPage().getLocalPartNoUnderscore();

        if (person.element1().getProperty(WikiProperties.STATUS.getPropertyName()).equals(NodeStatus.BASE.name())) {
            logger.debug("Node [{}] base -> full", wikiPage);

            person.element1().setProperty(WikiProperties.STATUS.getPropertyName(), NodeStatus.FULL.name());
            person.element1().setProperty(WikiProperties.DATE_OF_BIRTH.getPropertyName(), Neo4jDateFormat.serialize(wikiPerson.getDateOfBirth()));
            person.element1().setProperty(WikiProperties.DATE_OF_DEATH.getPropertyName(), Neo4jDateFormat.serialize(wikiPerson.getDateOfDeath()));
        } else {
            logger.debug("Node [{}] full", wikiPage);
        }

        return person;
    }

    private Tuple2<Node,Boolean> saveHouse(WikiNamedResource wikiHouse) {
        return saveBase(wikiHouse, WikiLabels.HOUSE);
    }

    private Tuple2<Node,Boolean> saveBase(WikiNamedResource wikiResource, Label label) {
        String wikiPage = wikiResource.getLocalPartNoUnderscore();
        Optional<Node> maybeNode = getNodeByWikiPage(wikiPage, label);

        if (!maybeNode.isPresent()) {
            logger.debug("Node [{}] null -> base", wikiPage);

            Node node = graphDb.createNode(label);
            node.setProperty(WikiProperties.STATUS.getPropertyName(), NodeStatus.BASE.name());
            node.setProperty(WikiProperties.WIKI_PAGE.getPropertyName(), wikiPage);
            node.setProperty(WikiProperties.NAME.getPropertyName(),
                    wikiResource.getDisplayText()!=null ? wikiResource.getDisplayText() : wikiPage);

            return new Tuple2<>(node, true);
        } else {
            logger.debug("Node [{}] exist", wikiPage);
            return new Tuple2<>(maybeNode.get(), false);
        }
    }

    private Optional<Node> getNodeByWikiPage(String wikiPage, Label label) {
        ResourceIterable<Node> existingNodes = graphDb.findNodesByLabelAndProperty(
                label, WikiProperties.WIKI_PAGE.getPropertyName(), wikiPage);
        ResourceIterator<Node> iterator = existingNodes.iterator();

        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    private List<WikiNamedResource> getRelations(Node person, RelationshipType relation) {
//        TraversalDescription traversal = Traversal.description()
        TraversalDescription traversal = graphDb.traversalDescription()
                .relationships(relation, Direction.OUTGOING)
                .evaluator(Evaluators.excludeStartPosition())
                .evaluator(Evaluators.atDepth(1));

        Traverser traverser = traversal.traverse(person);
        Iterable<Node> iterable = traverser.nodes();

        List<WikiNamedResource> ret = new ArrayList<>(1);

        for (Node spouse : iterable) {
            String spouseWikiPage = (String) spouse.getProperty(WikiProperties.WIKI_PAGE.getPropertyName());
            String spouseName = (String) spouse.getProperty(WikiProperties.NAME.getPropertyName());
            ret.add(new WikiNamedResource(spouseWikiPage, spouseName));
        }

        return ret;
    }


    private void setPropertyIfEmpty(Node node, WikiProperties property, Object value) {
        if (!node.hasProperty(property.getPropertyName())) {
            logger.debug("Node [{}] has no [{}] property. Setting it the value {}",
                    node.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()), property.getPropertyName(), value);
            node.setProperty(property.getPropertyName(), value);
        }
    }

    private void setRelationIfEmpty(Node node, Node otherNode, WikiRelationships relationship) {
        if (!node.hasRelationship(relationship)) {
            logger.debug("Node [{}] has no [{}] relation to node [{}]. Creating it now.");
            node.createRelationshipTo(otherNode, relationship);
        }
    }
}
