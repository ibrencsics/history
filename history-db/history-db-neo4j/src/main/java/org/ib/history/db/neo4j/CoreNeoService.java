package org.ib.history.db.neo4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.commons.utils.Neo4jDateFormat;
import org.ib.history.db.neo4j.data.*;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.ib.history.wiki.domain.WikiSuccession;
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
            saveSuccessions(person.element1(), wikiPerson);
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
    @Deprecated
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

    @Override
    public Optional<NeoPerson> getNeoPerson(String wikiPage) {
        logger.debug("getPerson([{}])", wikiPage);

        wikiPage = formatWikiPage(wikiPage);

        try (Transaction tx = graphDb.beginTx()) {
            Optional<Node> maybePerson = getNodeByWikiPage(wikiPage, WikiLabels.PERSON);

            if (isFull(maybePerson)) {
                Node node = maybePerson.get();

                logger.debug("Page [{}] found in cache", wikiPage);

                NeoPerson neoPerson = getBasicNeoPerson(node, wikiPage);

                List<WikiNamedResource> resources;

                resources = getRelations(node, WikiRelationships.HAS_FATHER);
                if (!resources.isEmpty()) {
                    NeoPerson father = getBasicNeoPerson(resources.get(0).getLocalPart());
                    neoPerson.setFather(Optional.of(father));
                }

                resources = getRelations(node, WikiRelationships.HAS_MOTHER);
                if (!resources.isEmpty()) {
                    NeoPerson mother = getBasicNeoPerson(resources.get(0).getLocalPart());
                    neoPerson.setMother(Optional.of(mother));
                }

                resources = getRelations(node, WikiRelationships.HAS_ISSUE);
                for (WikiNamedResource resource : resources) {
                    NeoPerson issue = getBasicNeoPerson(resource.getLocalPart());
                    neoPerson.addIssue(issue);
                }

                resources = getRelations(node, WikiRelationships.IS_SPOUSE_OF);
                for (WikiNamedResource resource : resources) {
                    NeoPerson spouse = getBasicNeoPerson(resource.getLocalPart());
                    neoPerson.addSpouse(spouse);
                }

                resources = getRelations(node, WikiRelationships.IN_HOUSE);
                for (WikiNamedResource resource : resources) {
                    neoPerson.addHouse(getNeoHouse(resource.getLocalPart()));
                }

                neoPerson.setSuccessions(getJobs(node));

                return Optional.of(neoPerson);
            } else {
                logger.debug("Page [{}] not found in cache", wikiPage);
                return Optional.empty();
            }
        }
    }


    // Save methods

    private void saveFamily(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getFather()!=null) {
            Tuple2<Node,Boolean> father = saveBase(wikiPerson.getFather(), WikiLabels.PERSON);

            setRelationIfEmpty(baseNode, father.element1(), WikiRelationships.HAS_FATHER);
            setRelationIfEmpty(father.element1(), baseNode, WikiRelationships.HAS_ISSUE);
            setPropertyIfEmpty(father.element1(), WikiProperties.GENDER, GenderType.MALE.name());
        }
        if (wikiPerson.getMother()!=null) {
            Tuple2<Node,Boolean> mother = saveBase(wikiPerson.getMother(), WikiLabels.PERSON);

            setRelationIfEmpty(baseNode, mother.element1(), WikiRelationships.HAS_MOTHER);
            setRelationIfEmpty(mother.element1(), baseNode, WikiRelationships.HAS_ISSUE);
            setPropertyIfEmpty(mother.element1(), WikiProperties.GENDER, GenderType.FEMALE.name());
        }
    }

    private void saveSpouses(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getSpouses()!=null) {
            wikiPerson.getSpouses().stream().forEach(s -> {
                Tuple2<Node,Boolean> spouse = saveBase(s, WikiLabels.PERSON);

                setRelationIfEmpty(baseNode, spouse.element1(), WikiRelationships.IS_SPOUSE_OF);
                setRelationIfEmpty(spouse.element1(), baseNode, WikiRelationships.IS_SPOUSE_OF);
            });
        }
    }

    private void saveIssues(Node baseNode, WikiPerson wikiPerson) {
        if (wikiPerson.getIssues()!=null) {
            wikiPerson.getIssues().stream().forEach(i -> {
                Tuple2<Node,Boolean> issue = saveBase(i, WikiLabels.PERSON);

                setRelationIfEmpty(baseNode, issue.element1(), WikiRelationships.HAS_ISSUE);
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

    private void saveSuccessions(Node baseNode, WikiPerson wikiPerson) {
        if (!baseNode.hasRelationship(WikiRelationships.AS)) {
            if (wikiPerson.getSuccessions() != null) {
                wikiPerson.getSuccessions().stream().forEach(succ -> {
                    if (succ.getTitleAndCountries().isPresent()) {
                        setJobIfEmpty(baseNode, succ);
                    }
                });
            }
        }
    }

    private void setJobIfEmpty(Node baseNode, WikiSuccession wikiSuccession) {

        Node jobNode = graphDb.createNode(WikiLabels.JOB);
        jobNode.setProperty(WikiProperties.JOB_FROM.getPropertyName(), Neo4jDateFormat.serialize(wikiSuccession.getFrom()));
        jobNode.setProperty(WikiProperties.JOB_TO.getPropertyName(), Neo4jDateFormat.serialize(wikiSuccession.getTo()));

        if (wikiSuccession.getTitleAndCountries().isPresent()) {
            Tuple2<String,List<String>> titleAndCountries = wikiSuccession.getTitleAndCountries().get();
            jobNode.setProperty(WikiProperties.JOB_TITLE.getPropertyName(), titleAndCountries.element1());

            titleAndCountries.element2().stream().forEach(country -> {
                Node countryNode = saveCountry(new WikiNamedResource("", country)).element1();
                jobNode.createRelationshipTo(countryNode, WikiRelationships.RULES);
            });
        } else if (wikiSuccession.getRaw().isPresent()) {
            jobNode.setProperty(WikiProperties.JOB_RAW.getPropertyName(), wikiSuccession.getRaw().get());
        }

        if (wikiSuccession.getPredecessor()!=null) {
            Node predecessorNode = savePerson(wikiSuccession.getPredecessor()).element1();
            jobNode.createRelationshipTo(predecessorNode, WikiRelationships.PREDECESSOR);
        }

        if (wikiSuccession.getSuccessor()!=null) {
            Node successorNode = savePerson(wikiSuccession.getSuccessor()).element1();
            jobNode.createRelationshipTo(successorNode, WikiRelationships.SUCCESSOR);
        }

        baseNode.createRelationshipTo(jobNode, WikiRelationships.AS);
    }

    // Create nodes

    private Tuple2<Node,Boolean> savePerson(WikiPerson wikiPerson) {
        Tuple2<Node,Boolean> person = savePerson(wikiPerson.getWikiNamedResource());
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

    private Tuple2<Node, Boolean> savePerson(WikiNamedResource wikiPerson) {
        return saveBase(wikiPerson, WikiLabels.PERSON);
    }

    private Tuple2<Node,Boolean> saveHouse(WikiNamedResource wikiHouse) {
        return saveBase(wikiHouse, WikiLabels.HOUSE);
    }

    private Tuple2<Node,Boolean> saveCountry(WikiNamedResource wikiCountry) {
        return saveBase(wikiCountry, WikiLabels.COUNTRY);
    }

    private Tuple2<Node,Boolean> saveBase(WikiNamedResource wikiResource, Label label) {
        String wikiPage = wikiResource.getLocalPartNoUnderscore();
        Optional<Node> maybeNode = Optional.empty();

        if (!"".equals(wikiPage)) {
            maybeNode = getNodeByWikiPage(wikiPage, label);
        }

        if (!maybeNode.isPresent()) {
            String name = wikiResource.getDisplayText();
            maybeNode = getNodeByName(name, label);
        }

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

    // Create properties, relations

    private void setPropertyIfEmpty(Node node, WikiProperties property, Object value) {
        if (!node.hasProperty(property.getPropertyName())) {
            logger.debug("Node [{}] has no [{}] property. Setting it the value {}",
                    node.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()), property.getPropertyName(), value);
            node.setProperty(property.getPropertyName(), value);
        }
    }

    private void setPropertyIfEmpty(Relationship relation, WikiProperties property, Object value) {
        if (!relation.hasProperty(property.getPropertyName())) {
            logger.debug("Relation [{} -> {}] has no [{}] property. Setting it the value {}",
                    relation.getStartNode().getProperty(WikiProperties.WIKI_PAGE.getPropertyName()),
                    relation.getEndNode().getProperty(WikiProperties.WIKI_PAGE.getPropertyName()),
                    property.getPropertyName(), value);
            relation.setProperty(property.getPropertyName(), value);
        }
    }

    private Relationship setRelationIfEmpty(Node node, Node otherNode, WikiRelationships relationship) {
        boolean found = false;

        Iterator<Relationship> iter = node.getRelationships(relationship).iterator();
        while (iter.hasNext()) {
            Relationship rel = iter.next();
            if (otherNode.equals(rel.getEndNode())) {
                found = true;
                return rel;
            }
        }

        if (!found) {
            logger.debug("Node [{}] has no [{}] relation to node [{}]. Creating it now.",
                    node.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()),
                    relationship.name(),
                    otherNode.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()));
            return node.createRelationshipTo(otherNode, relationship);
        }

        throw new RuntimeException("");
    }

    // Queries

    private NeoPerson getBasicNeoPerson(String wikiPage) {
        Optional<Node> maybeNode = getNodeByWikiPage(wikiPage, WikiLabels.PERSON);
        if (maybeNode.isPresent()) {
            return getBasicNeoPerson(maybeNode.get(), wikiPage);
        }

        throw new RuntimeException("WikiPage not found: " + wikiPage);
    }

    private NeoPerson getBasicNeoPerson(Node node) {
        if (node.hasProperty(WikiProperties.WIKI_PAGE.getPropertyName())) {
            return getBasicNeoPerson(node, (String) node.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()));
        }

        throw new RuntimeException("WikiPage not found: " + node);
    }

    private NeoPerson getBasicNeoPerson(Node node, String wikiPage) {
        NeoPerson person = getBaseData(node, wikiPage, NeoPerson.class);

        if (node.hasProperty(WikiProperties.DATE_OF_BIRTH.getPropertyName())) {
            person.setDateOfBirth(
                    Neo4jDateFormat.parse((String) node.getProperty(WikiProperties.DATE_OF_BIRTH.getPropertyName()))
            );
        }

        if (node.hasProperty(WikiProperties.DATE_OF_DEATH.getPropertyName())) {
            person.setDateOfDeath(
                    Neo4jDateFormat.parse((String) node.getProperty(WikiProperties.DATE_OF_DEATH.getPropertyName()))
            );
        }

        if (node.hasProperty(WikiProperties.GENDER.getPropertyName())) {
            person.setGender(
                    GenderType.valueOf((String) node.getProperty(WikiProperties.GENDER.getPropertyName()))
            );
        }

        return person;
    }

    private NeoHouse getNeoHouse(String wikiPage) {
        Optional<Node> maybeNode = getNodeByWikiPage(wikiPage, WikiLabels.HOUSE);
        if (maybeNode.isPresent()) {
            return getNeoHouse(maybeNode.get(), wikiPage);
        }

        throw new RuntimeException("WikiPage not found: " + wikiPage);
    }

    private NeoHouse getNeoHouse(Node node, String wikiPage) {
        return getBaseData(node, wikiPage, NeoHouse.class);
    }

    private NeoCountry getNeoCountry(Node node) {
        if (node.hasProperty(WikiProperties.WIKI_PAGE.getPropertyName())) {
            return getBaseData(node, (String) node.getProperty(WikiProperties.WIKI_PAGE.getPropertyName()), NeoCountry.class);
        }

        throw new RuntimeException("WikiPage not found: " + node);
    }

    private <T extends NeoBaseData> T getBaseData(Node node, String wikiPage, Class<T> type) {
        try {
            T inst = type.newInstance();
            inst.setWikiPage(wikiPage);
            inst.setName((String) node.getProperty(WikiProperties.NAME.getPropertyName()));
            return inst;
        } catch (InstantiationException e) {
            throw  new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw  new IllegalArgumentException(e);
        }
    }

    private Optional<Node> getNodeByWikiPage(String wikiPage, Label label) {
        ResourceIterable<Node> existingNodes = graphDb.findNodesByLabelAndProperty(
                label, WikiProperties.WIKI_PAGE.getPropertyName(), wikiPage);
        ResourceIterator<Node> iterator = existingNodes.iterator();

        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    private Optional<Node> getNodeByName(String name, Label label) {
        ResourceIterable<Node> existingNodes = graphDb.findNodesByLabelAndProperty(
                label, WikiProperties.NAME.getPropertyName(), name);
        ResourceIterator<Node> iterator = existingNodes.iterator();

        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }

    private List<WikiNamedResource> getRelations(Node person, RelationshipType relation) {
        Iterable<Node> iterable = queryRelations(person, relation);

        List<WikiNamedResource> ret = new ArrayList<>(1);

        for (Node mode : iterable) {
            String nodeWikiPage = (String) mode.getProperty(WikiProperties.WIKI_PAGE.getPropertyName());
            String nodeName = (String) mode.getProperty(WikiProperties.NAME.getPropertyName());
            ret.add(new WikiNamedResource(nodeWikiPage, nodeName));
        }

        return ret;
    }

    private List<NeoSuccession> getJobs(Node person) {
        Iterable<Node> iterable = queryRelations(person, WikiRelationships.AS);

        List<NeoSuccession> successions = new ArrayList<>(3);

        for (Node jobNode : iterable) {
            NeoSuccession succ = new NeoSuccession();

            if (jobNode.hasProperty(WikiProperties.JOB_FROM.getPropertyName())) {
                succ.setFrom(Neo4jDateFormat.parse((String) jobNode.getProperty(WikiProperties.JOB_FROM.getPropertyName())));
            }

            if (jobNode.hasProperty(WikiProperties.JOB_TO.getPropertyName())) {
                succ.setTo(Neo4jDateFormat.parse((String) jobNode.getProperty(WikiProperties.JOB_TO.getPropertyName())));
            }

            if (jobNode.hasProperty(WikiProperties.JOB_TITLE.getPropertyName())) {
                succ.setTitle((String) jobNode.getProperty(WikiProperties.JOB_TITLE.getPropertyName()));
            }

            // supports only one predecessor now
            queryRelations(jobNode, WikiRelationships.PREDECESSOR).forEach(node -> {
                succ.setPredecessor(getBasicNeoPerson(node));
            });

            // supports only one successor now
            queryRelations(jobNode, WikiRelationships.SUCCESSOR).forEach(node -> {
                succ.setSuccessor(getBasicNeoPerson(node));
            });

            queryRelations(jobNode, WikiRelationships.RULES).forEach(node -> {
                succ.addCountry(getNeoCountry(node));
            });

            successions.add(succ);
        }

        return successions;
    }

    private Iterable<Node> queryRelations(Node node, RelationshipType relation) {
//        TraversalDescription traversal = Traversal.description()
        TraversalDescription traversal = graphDb.traversalDescription()
                .relationships(relation, Direction.OUTGOING)
                .evaluator(Evaluators.excludeStartPosition())
                .evaluator(Evaluators.atDepth(1));

        Traverser traverser = traversal.traverse(node);
        Iterable<Node> iterable = traverser.nodes();

        return iterable;
    }

    // Misc

    private String formatWikiPage(String wikiPage) {
        return wikiPage.replace("_", " ");
    }

    private boolean isFull(Optional<Node> node) {
        return  (node.isPresent() &&
                node.get().getProperty(WikiProperties.STATUS.getPropertyName())
                        .equals(NodeStatus.FULL.name()));
    }
}
