package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.DateWrapper;
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
public class PersonServiceImplTest {

    @Autowired
    PersonService personService;

    @Test
    @Rollback(false)
    public void test() {

        PersonData personData =
                new PersonData.Builder().name("William")
                        .dateOfDeath(new DateWrapper.Builder().year(1087).noMonth().noDay().build())
                        .child(new PersonData.Builder().name("William2").build())
                        .locale("DE", new PersonData.Builder().name("Wilhelm").build())
                        .locale("HU", new PersonData.Builder().name("Vilmos").build())
                        .build();

        personService.addPerson(personData);
    }
}
