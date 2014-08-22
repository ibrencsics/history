package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class RulerServiceImplTest {

    @Autowired
    HouseService houseService;

    @Autowired
    CountryService countryService;

    @Autowired
    PersonService personService;

    @Autowired
    RulerService rulerService;

    @Test
//    @Rollback(false)
    public void test() {

        HouseData houseData = new HouseData.Builder().name("House of Normandy")
                .locale("DE", new HouseData.Builder().name("Normannische Dynastie").build())
                .locale("HU", new HouseData.Builder().name("Normandiai Ház").build())
                .build();
        HouseData houseDataCreated = houseService.addHouse(houseData);

        PersonData personData = new PersonData.Builder().name("William")
                .dateOfDeath(new FlexibleDate.Builder().year(1087).noMonth().noDay().build())
                .locale("DE", new PersonData.Builder().name("Wilhelm").build())
                .locale("HU", new PersonData.Builder().name("Vilmos").build())
                .house(houseDataCreated)
                .build();
        PersonData personDataCreated = personService.addPerson(personData);
        assertNotNull(personDataCreated.getId());
        assertEquals(personDataCreated.getLocales().size(), 2);

        CountryData countryData = new CountryData.Builder().name("England").build();
        CountryData countryDataCreated = countryService.addCountry(countryData);

        RulerData.RulesData rulesData = new RulerData.RulesData.Builder()
                .to(new FlexibleDate.Builder().year(1087).noMonth().noDay().build()).country(countryDataCreated).build();

        RulerData rulerData = new RulerData.Builder()
                .name("I. William").alias("William the Conquerer").title("King").job(rulesData)
                .locale("DE", new RulerData.Builder().name("Wilhelm I").alias("Wilhelm der Eroberer").title("König").build())
                .locale("HU", new RulerData.Builder().name("I. Vilmos").alias("Hódító Vilmos").title("Király").build())
                .build();
        RulerData rulerDataCreated = rulerService.addRuler(personDataCreated, rulerData);
        assertNotNull(rulerDataCreated.getId());


        Set<RulerData> rulerDataSet = rulerService.getAllRulers();
        assertEquals(1, rulerDataSet.size());

        rulerDataSet = rulerService.getRulers(personDataCreated);
        assertEquals(1, rulerDataSet.size());
    }
}
