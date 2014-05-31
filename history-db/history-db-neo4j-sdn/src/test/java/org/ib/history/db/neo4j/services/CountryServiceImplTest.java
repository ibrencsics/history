package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class CountryServiceImplTest {

    @Autowired
    CountryService countryService;

    @Test
//    @Rollback(false)
    public void test() {

        CountryData countryData =
                new CountryData.Builder().name("Hungary")
                        .locale("DE", new CountryData.Builder().name("Ungarn").build())
                        .locale("HU", new CountryData.Builder().name("Magyarország").build())
                        .build();
        CountryData countryDataCreated = countryService.addCountry(countryData);
        assertNotNull(countryDataCreated.getId());
        assertEquals(countryDataCreated.getLocales().size(), 2);

        countryDataCreated.getLocale("HU").setName("Hungária");
        countryDataCreated = countryService.addCountry(countryDataCreated);
        assertEquals(countryDataCreated.getLocales().get("HU").getName(), "Hungária");

        countryDataCreated.addLocale("SR", new CountryData.Builder().name("Mađarska").build());
        countryDataCreated = countryService.addCountry(countryDataCreated);
        assertEquals(countryDataCreated.getLocales().size(), 3);

        List<CountryData> allCountries = countryService.getCountries();
        assertEquals(allCountries.size(), 1);
        assertEquals(allCountries.get(0).getLocales().size(), 3);

        countryService.deleteCountry(countryDataCreated);

        allCountries = countryService.getCountries();
        assertEquals(allCountries.size(), 0);
    }
}
