package org.ib.history.wiki.dbpedia;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.ib.history.wiki.service.WikiService;

import java.net.URISyntaxException;

public class WikiServiceDbpedia implements WikiService {

    private static final String PERSON_QUERY =
            "PREFIX : <http://dbpedia.org/resource/>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX d: <http://dbpedia.org/property/>\n" +
            "\n" +
            "select ?label ?dob ?dod ?father ?mother where {\n" +
            "  ?resource " +
                    "rdfs:label ?label; " +
                    "d:dateOfBirth ?dob; " +
                    "d:dateOfDeath ?dod; " +
                    "d:father ?father; " +
                    "d:mother ?mother\n" +
                    "FILTER ( lang(?label) = \"en\" )" +
            "}";

    @Override
    public WikiPerson getPerson(String wikiPage) {
        WikiResource wikiResource = WikiResource.fromLocalPart(wikiPage);

        WikiPerson.Builder builder = new WikiPerson.Builder();
        builder.wikiPage(WikiResource.fromLocalPart(wikiPage));

        ParameterizedSparqlString qs = new ParameterizedSparqlString(PERSON_QUERY);

        Resource resource = ResourceFactory.createResource(wikiResource.getFullDbpedia().toString());
        qs.setParam("resource", resource);

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());
        ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

        while ( results.hasNext() ) {
            QuerySolution querySolution = results.next();

            Literal literalLabel = querySolution.getLiteral("label");
            builder.name(literalLabel.getString());

            Literal literalDateOfBirth = querySolution.getLiteral("dob");
            builder.dateOfBirth(toFlexDate(literalDateOfBirth));

            Literal literalDateOfDeath = querySolution.getLiteral("dod");
            builder.dateOfDeath(toFlexDate(literalDateOfDeath));

            Resource resourceFather = querySolution.getResource("father");
            builder.father(WikiResource.fromURIString(resourceFather.getURI()));

            Resource resourceMother = querySolution.getResource("mother");
            builder.mother(WikiResource.fromURIString(resourceMother.getURI()));
        }

        ResultSetFormatter.out( results );

        return builder.build();
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
}
