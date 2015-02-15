package org.ib.history.db.neo4j.repositories;

import org.apache.poi.ss.formula.functions.Count;
import org.ib.history.db.neo4j.domain.BaseEntityWithTranslation;
import org.ib.history.db.neo4j.domain.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository repo;

    @Test
    public void test() {
        // save
        Country country = new Country();
        country.setName("England");
        country.setDefaultLocale(true);
        repo.save(country);

        // query
        List<Country> countryList = repo.getCountries();
        assertEquals(1, countryList.size());

        // add translation
        Country countryRead = countryList.get(0);

        Country de = new Country();
        de.setName("England");
        de.setDefaultLocale(false);
        Country hu = new Country();
        hu.setName("Anglia");
        hu.setDefaultLocale(false);

        Country.Translation<Country> deTranslation = new BaseEntityWithTranslation.Translation(countryRead, de, "DE");
        Country.Translation<Country> huTranslation = new BaseEntityWithTranslation.Translation(countryRead, hu, "HU");

        countryRead.getLocales().add(deTranslation);
        countryRead.getLocales().add(huTranslation);

        repo.save(deTranslation.getTranslation());
        repo.save(huTranslation.getTranslation());
        repo.save(countryRead);

        // query

        List<Country> countryList2 = repo.getCountries();
        assertEquals(1, countryList2.size());

        Country countryRead2 = countryList.get(0);
        assertEquals(2, countryRead2.getLocales().size());
    }
}
