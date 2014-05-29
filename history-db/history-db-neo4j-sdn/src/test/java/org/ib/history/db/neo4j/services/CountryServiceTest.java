package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.CountryDto;
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
public class CountryServiceTest /*extends AbstractIntegrationTest*/ {

    @Autowired
    CountryService countryService;

    @Test
//    @Rollback(false)
    public void test() {
//        CountryDto countryDto = new CountryDto().withName("Hungary");
//        CountryDto countryDtoCreated = countryService.addCountry(countryDto);
//        System.out.println(countryDtoCreated);
//        countryService.addCountry(countryDtoCreated);

        CountryData countryData =
                new CountryData.Builder().name("Hungary")
                        .locale("DE", new CountryData.Builder().name("Ungarn").build())
                        .locale("HU", new CountryData.Builder().name("Magyarország").build())
                        .build();
        countryService.addCountry(countryData);

        System.out.println(countryService.getCountries());
    }
}
