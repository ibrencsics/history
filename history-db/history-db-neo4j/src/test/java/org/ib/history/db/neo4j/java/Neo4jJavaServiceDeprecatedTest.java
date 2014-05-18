package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.Refdata;
import org.junit.Test;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.logging.BufferingLogger;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Neo4jJavaServiceDeprecatedTest {

//    @Test
    public void test() {
        GraphDatabaseService db = new TestGraphDatabaseFactory().newImpermanentDatabase();

        ExecutionEngine engine = new ExecutionEngine( db , new BufferingLogger() );

        ExecutionResult result;
        try ( Transaction ignored = db.beginTx() )
        {

            Map<String,String> props = new HashMap<>();
            props.put("name", "England");
            props.put("capital", "London");

            Map<String,Object> params = new HashMap<>();
            params.put("props", props);

            result = engine.execute( "create (england:Country { props })", params );

            ignored.success();

            result = engine.execute("match (n) return n.name, n.capital");
            System.out.println(result.dumpToString());
        }
    }

    @Test
    public void countryTest() {
        Neo4jJavaServiceDeprecated service = new Neo4jJavaServiceDeprecated();

        CountryDto country = new CountryDto().withName("England");

        service.putCountry(country);
        List<CountryDto> countries = service.getCountries();
        assertEquals(countries.size(), 1);

        CountryDto localeCountry = new CountryDto().withName("Anglia");
        service.putCountry(country, localeCountry, new Locale("HU"));
        countries = service.getCountries(new Locale("HU"));
        assertEquals(countries.size(), 1);
    }

    @Test
    public void countryLocalizedTest() {
        Neo4jJavaServiceDeprecated service = new Neo4jJavaServiceDeprecated();

        LocalizedDto<CountryDto> country = Refdata.getHungary();

        // put country
        service.putCountry(country);

        // get default
        List<CountryDto> countries = service.getCountries();
        assertEquals(countries.size(), 1);

        // get locale
        countries = service.getCountries(new Locale("HU"));
        assertEquals(countries.size(), 1);
    }

    @Test
    public void testDeleteCountry() {
        Neo4jJavaServiceDeprecated service = new Neo4jJavaServiceDeprecated();

        LocalizedDto<CountryDto> country = Refdata.getHungary();

        // put country
        service.putCountry(country);

        // delete
        service.deleteCountry(country.getDefaultLocaleElement());

        // get default
        List<CountryDto> countries = service.getCountries();
        assertEquals(countries.size(), 0);

        // get locale
        countries = service.getCountries(new Locale("HU"));
        assertEquals(countries.size(), 0);
    }
}
