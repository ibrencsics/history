package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.Neo4jCountryService;
import org.ib.history.db.neo4j.Refdata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class DefaultNeo4jCountryServiceImplTest {

    @Autowired
    private Neo4jCountryService service;

    @Test
    public void test() {
        System.setProperty("org.neo4j.rest.batch_transaction", "true");

        LocalizedDto<CountryDto> hungary = Refdata.getHungary();
        LocalizedDto<CountryDto> england = Refdata.getEngland();

        service.putCountry(hungary);
//        System.out.println(service.getCountries());
//        service.putCountry(england);
//        System.out.println(service.getCountries());
//        service.deleteCountry(england);
//        System.out.println(service.getCountries());

//        hungary.getLocales().get(new Locale("HU")).withName("Hungária");
//        service.putCountry(hungary);
//        System.out.println(service.getCountries());
//
//        System.out.println(service.getCountryById(0L));
//        try {
//            System.out.println(service.getCountryById(1L));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println(service.getCountryByName("Hungary"));
//        try {
//            System.out.println(service.getCountryByName("asd"));
//        } catch (Exception e) {
//            System.out.println("No result");
//        }
//
//        System.out.println(service.getCountryByName("Ungarn", Locale.GERMAN));
    }
}
