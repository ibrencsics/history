package org.ib.history.db.neo4j.rest;

import org.ib.history.commons.data.CountryDto;
import org.junit.Test;

import java.util.List;

public class Neo4jRestServiceTest {

    @Test
    public void testGetCountries() {

        Neo4jRestService service = new Neo4jRestService();

        List<CountryDto> countries = service.getCountries();

        System.out.println("countries:");
        for (CountryDto country : countries) {
            System.out.println("  " + country);
        }
    }
}
