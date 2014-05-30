package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class CountryServiceTest {

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
        System.out.println(countryDataCreated);

        countryDataCreated.getLocale("HU").setName("Hungária");
        countryDataCreated = countryService.addCountry(countryDataCreated);
        System.out.println(countryDataCreated);

        countryDataCreated.addLocale("SR", new CountryData.Builder().name("Mađarska").build());
        countryDataCreated = countryService.addCountry(countryDataCreated);
        System.out.println(countryDataCreated);

        System.out.println(countryService.getCountries());
    }
}
