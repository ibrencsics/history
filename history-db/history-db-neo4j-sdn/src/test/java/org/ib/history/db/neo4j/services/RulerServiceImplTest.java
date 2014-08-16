package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class RulerServiceImplTest {

    @Autowired
    CountryService countryService;

    @Autowired
    PersonService personService;

    @Autowired
    RulerService rulerService;

    @Test
//    @Rollback(false)
    public void test() {

        PersonData personData = new PersonData.Builder().name("William")
                .dateOfDeath(new FlexibleDate.Builder().year(1087).noMonth().noDay().build()).build();
        PersonData personDataCreated = personService.addPerson(personData);
        assertNotNull(personDataCreated.getId());

        CountryData countryData = new CountryData.Builder().name("England").build();
        CountryData countryDataCreated = countryService.addCountry(countryData);

        RulerData.RulesData rulesData = new RulerData.RulesData.Builder()
                .to(new FlexibleDate.Builder().year(1087).noMonth().noDay().build()).country(countryDataCreated).build();

        RulerData rulerData = new RulerData.Builder().name("I. William")
                .alias("William the Conquerer").title("King").job(rulesData).build();
        RulerData rulerDataCreated = rulerService.addRuler(personDataCreated, rulerData);
        assertNotNull(rulerDataCreated.getId());
    }
}
