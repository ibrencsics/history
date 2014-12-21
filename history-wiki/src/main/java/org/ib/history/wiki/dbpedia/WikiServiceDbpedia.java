package org.ib.history.wiki.dbpedia;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.ib.history.wiki.service.WikiService;

import java.net.URISyntaxException;

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

    private static final String PERSON_QUERY_FAMILY = "" +
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "select ?spouse ?issue where {\n" +
            "  OPTIONAL {?resource (d:spouse | d:spouses) ?spouse . }\n" +
            "  OPTIONAL {?resource d:issue ?issue}\n" +
            "    \n" +
            "}";

    @Override
    public WikiPerson getPerson(String wikiPage) {
        WikiResource wikiResource = WikiResource.fromLocalPart(wikiPage);

        WikiPerson.Builder builder = new WikiPerson.Builder();
        builder.wikiPage(WikiResource.fromLocalPart(wikiPage));

        queryBasicData(builder, wikiResource);
        queryDateOfBirth(builder, wikiResource);
        queryDateOfDeath(builder, wikiResource);

//        ResultSetFormatter.out( results );

        return builder.build();
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
