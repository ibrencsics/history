package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PersonData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class RulerServiceImplTest {

    @Autowired
    PersonService personService;

    @Autowired
    RulerService rulerService;

    @Test
    public void test() {

        PersonData person = new PersonData.Builder().name("William")
                .dateOfDeath(new FlexibleDate.Builder().year(1087).noMonth().noDay().build()).build();

        PersonData personCreated = personService.addPerson(person);
        assertNotNull(personCreated.getId());

    }
}
