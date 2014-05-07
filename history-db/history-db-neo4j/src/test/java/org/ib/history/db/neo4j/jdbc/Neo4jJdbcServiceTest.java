package org.ib.history.db.neo4j.jdbc;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.RulerDto;
import org.ib.history.commons.data.LocalizedDto;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

public class Neo4jJdbcServiceTest {

//    @Test
    public void testGetEmpirors() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        List<RulerDto> empirors = service.getRulers();
        System.out.println(empirors);
    }

//    @Test
    public void testGetEmpirorsLocale() {
        Neo4jJdbcService service = new Neo4jJdbcService();
        List<RulerDto> empirors = service.getRulers(new Locale("hu"));
        System.out.println(empirors);
    }

    @Test
    public void testCountry() {
        Neo4jJdbcService service = getNeo4jJdbcService();

        // empty list
        List<CountryDto> countries = service.getCountries();
        assertEquals(countries.size(), 0);

        // put country
        CountryDto country = new CountryDto();
        country.setName("Hungary");
        service.putCountry(country);
        countries = service.getCountries();
        assertEquals(countries.size(), 1);

        // put country
        country = new CountryDto();
        country.setName("England");
        service.putCountry(country);
        countries = service.getCountries();
        assertEquals(countries.size(), 2);
    }

    @Test
    public void testCountryLocale() {
        Neo4jJdbcService service = getNeo4jJdbcService();

        // empty list
        List<CountryDto> countries = service.getCountries(Locale.GERMAN);
        assertEquals(countries.size(), 0);

        CountryDto country = new CountryDto();
        country.setName("Hungary");

        CountryDto countryLocale = new CountryDto();
        countryLocale.setName("Ungarn");

        // no default country -> nothing stored
        service.putCountry(country, countryLocale, Locale.GERMAN);
        countries = service.getCountries();
        assertEquals(countries.size(), 0);

        // put default country
        service.putCountry(country);
        countries = service.getCountries();
        assertEquals(countries.size(), 1);

        // put locale
        service.putCountry(country, countryLocale, Locale.GERMAN);

        // get locale
        List<CountryDto> localeCountries = service.getCountries(Locale.GERMAN);
        assertEquals(localeCountries.size(), 1);

        // get default
        countries = service.getCountries();
        assertEquals(countries.size(), 1);
    }

    @Test
    public void testLocalizedCountry() {
        Neo4jJdbcService service = getNeo4jJdbcService();

        LocalizedDto country = getHungary();

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
        Neo4jJdbcService service = getNeo4jJdbcService();

        LocalizedDto<CountryDto> country = getHungary();

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

    private Neo4jJdbcService getNeo4jJdbcService() {
        return new Neo4jJdbcService("jdbc:neo4j:mem");
    }

    /**
     * Refdata
     */

    private LocalizedDto<CountryDto> getHungary() {
        LocalizedDto<CountryDto> country = new LocalizedDto<CountryDto>();
        country.setDefaultLocaleElement(new CountryDto().withName("Hungary"));
        country.addLocaleElement(new CountryDto().withName("Ungarn"), Locale.GERMAN);
        country.addLocaleElement(new CountryDto().withName("Magyarország"), new Locale("HU"));
        return country;
    }
}
