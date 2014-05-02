package org.ib.history.db.neo4j.jdbc;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.EmpirorDto;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

public class Neo4jJdbcServiceTest {

//    @Test
    public void testGetCountries() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        List<CountryDto> countries = service.getCountries();
        System.out.println(countries);
    }

//    @Test
    public void testGetCountriesLocale() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        List<CountryDto> countries = service.getCountries(Locale.GERMAN);
        System.out.println(countries);
    }

    @Test
    public void testPutCountry() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        CountryDto country = new CountryDto();
        country.setName("Hungary");
        service.putCountry(Locale.ENGLISH, country);
    }

//    @Test
    public void testGetEmpirors() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        List<EmpirorDto> empirors = service.getEmpirors();
        System.out.println(empirors);
    }

//    @Test
    public void testGetEmpirorsLocale() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        List<EmpirorDto> empirors = service.getEmpirors(new Locale("hu"));
        System.out.println(empirors);
    }
}
