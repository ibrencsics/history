package org.ib.history.wiki.dbpedia;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;

public class WilliamExample {
    public static void main(String[] args) {
        ParameterizedSparqlString qs = new ParameterizedSparqlString( "" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "PREFIX d: <http://dbpedia.org/property/>\n" +
                "\n" +
                "select ?db ?dd ?f ?m where {\n" +
//                "select ?resource where {\n" +
//                "  ?resource rdfs:label ?label\n" +
                "  ?resource d:dateOfBirth ?db; d:dateOfDeath ?dd; d:father ?f; d:mother ?m\n" +
                "}" );

//        Literal london = ResourceFactory.createLangLiteral( "William III of England", "en" );
//        qs.setParam( "label", london );
        Resource resource = ResourceFactory.createResource("http://dbpedia.org/resource/William_III_of_England");
        qs.setParam("resource", resource);

        System.out.println( qs );

        QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

        // Normally you'd just do results = exec.execSelect(), but I want to
        // use this ResultSet twice, so I'm making a copy of it.
        ResultSet results = ResultSetFactory.copyResults(exec.execSelect());

        while ( results.hasNext() ) {
            // As RobV pointed out, don't use the `?` in the variable
            // name here. Use *just* the name of the variable.
            QuerySolution querySolution = results.next();

            Literal literalDateOfBirth = querySolution.getLiteral("db");
            XSDDateTime dateOfBirth = (XSDDateTime) literalDateOfBirth.getValue();

            Resource resourceFather = querySolution.getResource("f");
            System.out.println(resourceFather);
        }

        // A simpler way of printing the results.
        ResultSetFormatter.out( results );
    }
}