package org.ib.history.wiki.dbpedia;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.ib.history.wiki.service.WikiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WikiServiceDbpedia implements WikiService {

    private static final String PERSON_QUERY =
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "select ?label ?father ?mother where {\n" +
            "  ?resource " +
                    "rdfs:label ?label; " +
                    "d:father ?father; " +
                    "d:mother ?mother\n" +
                    "FILTER ( lang(?label) = \"en\" )" +
            "}";

    private static final String PERSON_QUERY_BIRTH_DATE = "" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "select ?birthDate where {\n" +
            "  ?resource (d:dateOfBirth | d:birthDate) ?birthDate . \n" +
            "  FILTER ( datatype(?birthDate) = xsd:date )\n" +
            "}";

    private static final String PERSON_QUERY_DEATH_DATE = "" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
            "select ?deathDate where {\n" +
            "  ?resource (d:dateOfDeath | d:deathDate) ?deathDate . \n" +
            "  FILTER ( datatype(?deathDate) = xsd:date )\n" +
            "}";

    private static final String PERSON_QUERY_SPOUSE = "" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "select ?spouse where {\n" +
            "  OPTIONAL {?resource (d:spouse | d:spouses) ?spouse . }\n" +
            "    \n" +
            "}";

    private static final String PERSON_QUERY_ISSUE = "" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "select ?issue where {\n" +
            "  OPTIONAL {?resource d:issue ?issue}\n" +
            "    \n" +
            "}";

    private static final String PERSON_QUERY_HOUSE = "" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "select ?house where {\n" +
            "  OPTIONAL {?resource d:house ?house}\n" +
            "    \n" +
            "}";

    @Override
    public Optional<WikiPerson> getPerson(String wikiPage) {
        WikiResource wikiResource = WikiResource.fromLocalPart(wikiPage);

        WikiPerson.Builder builder = new WikiPerson.Builder();
        builder.wikiPage(WikiResource.fromLocalPart(wikiPage));

        queryBasicData(builder, wikiResource);
        queryDateOfBirth(builder, wikiResource);
        queryDateOfDeath(builder, wikiResource);
        querySpouse(builder, wikiResource);
        queryIssue(builder, wikiResource);
        queryHouse(builder, wikiResource);

//        ResultSetFormatter.out( results );

        return Optional.of(builder.build());
    }

    private void queryBasicData(WikiPerson.Builder builder, WikiResource wikiResource) {

        ResultSet results = query(wikiResource, PERSON_QUERY);

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();

            Literal literalLabel = querySolution.getLiteral("label");
            builder.name(literalLabel.getString());

            Resource resourceFather = querySolution.getResource("father");
            builder.father(WikiNamedResource.fromURIString(resourceFather.getURI()));

            Resource resourceMother = querySolution.getResource("mother");
            builder.mother(WikiNamedResource.fromURIString(resourceMother.getURI()));
        }
    }

    private void queryDateOfBirth(WikiPerson.Builder builder, WikiResource wikiResource) {
        ResultSet results = query(wikiResource, PERSON_QUERY_BIRTH_DATE);

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();

            Literal literalDateOfBirth = querySolution.getLiteral("birthDate");
            builder.dateOfBirth(toFlexDate(literalDateOfBirth));
        }
    }

    private void queryDateOfDeath(WikiPerson.Builder builder, WikiResource wikiResource) {
        ResultSet results = query(wikiResource, PERSON_QUERY_DEATH_DATE);

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();

            Literal literalDateOfBirth = querySolution.getLiteral("deathDate");
            builder.dateOfDeath(toFlexDate(literalDateOfBirth));
        }
    }

    private void querySpouse(WikiPerson.Builder builder, WikiResource wikiResource) {
        ResultSet results = query(wikiResource, PERSON_QUERY_SPOUSE);
        List<RDFNode> spouses = new ArrayList<>(3);

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();
            spouses.add(querySolution.get("spouse"));
        }

        builder.spouse(toWikiNamedResource(spouses));
    }

    private void queryIssue(WikiPerson.Builder builder, WikiResource wikiResource) {
        ResultSet results = query(wikiResource, PERSON_QUERY_ISSUE);
        List<RDFNode> issues = new ArrayList<>(3);

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();
            issues.add(querySolution.get("issue"));
        }

        builder.issue(toWikiNamedResource(issues));
    }

    private void queryHouse(WikiPerson.Builder builder, WikiResource wikiResource) {
        ResultSet results = query(wikiResource, PERSON_QUERY_HOUSE);
        List<RDFNode> houses = new ArrayList<>(1);

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();
            houses.add(querySolution.get("house"));
        }

        builder.house(toWikiNamedResource(houses));
    }


    private ResultSet query(WikiResource wikiResource, String queryStr) {
        ParameterizedSparqlString qs = new ParameterizedSparqlString(queryStr);

        Resource resource = ResourceFactory.createResource(wikiResource.getFullDbpedia().toString());
        qs.setParam("resource", resource);

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());
        ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

        return results;
    }

    private FlexibleDate toFlexDate(Literal literal) {
        FlexibleDate.Builder builder = new FlexibleDate.Builder();

        if (literal.getValue() instanceof XSDDateTime) {
            XSDDateTime xsdDateTime = (XSDDateTime) literal.getValue();
            builder.year(xsdDateTime.getYears()).month(xsdDateTime.getMonths()).day(xsdDateTime.getDays());
        } else if (literal.getValue() instanceof Integer) {
            Integer year = (Integer) literal.getValue();
            builder.year(year).noMonth().noDay();
        } else {
            System.out.println("class: " + literal.getClass());
            System.out.println("class: " + literal.getValue().getClass());
        }

        return builder.build();
    }

    private List<WikiNamedResource> toWikiNamedResource(List<RDFNode> rdfNodes) {
        List<WikiNamedResource> ret = new ArrayList<>(3);

        for (RDFNode rdfNode : rdfNodes) {

            if (rdfNode instanceof Resource) {
                Resource resource = (Resource) rdfNode;
                WikiNamedResource spouseWikiResource = WikiNamedResource.fromURIString(resource.getURI());
                ret.add(spouseWikiResource);
            } else if (rdfNode instanceof Literal) {
                Literal literal = (Literal) rdfNode;
                String[] tokens = literal.getString().split("\\*");
                List<WikiNamedResource> namedResources = Arrays.asList(tokens).stream()
                        .filter(s -> !s.isEmpty())
                        .map(s -> WikiNamedResource.fromLocalPart(s))
                        .collect(Collectors.toList());
                ret.addAll(namedResources);
            }
        }

        return ret;
    }

//    PREFIX : <http://dbpedia.org/resource/>
//    PREFIX d: <http://dbpedia.org/property/>
//    select ?label ?dob ?dod ?father ?mother where {
//        :Anne_of_Austria rdfs:label ?label .
//                OPTIONAL{ :Anne_of_Austria (d:dateOfBirth | d:birthDate) ?dob . }
//        OPTIONAL{ :Anne_of_Austria (d:deathDate) ?dod .}
//        :Anne_of_Austria d:father ?father .
//                :Anne_of_Austria d:mother ?mother
//        FILTER ( lang(?label) = "en" )
//    }
}
