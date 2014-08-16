package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class PersonServiceImplTest {

    @Autowired
    PersonService personService;

    @Test
//    @Rollback(false)
    public void test() {

        PersonData personData =
                new PersonData.Builder().name("William")
                        .dateOfDeath(new FlexibleDate.Builder().year(1087).noMonth().noDay().build())
                        .child(new PersonData.Builder().name("William2").build())
                        .locale("DE", new PersonData.Builder().name("Wilhelm").build())
                        .locale("HU", new PersonData.Builder().name("Vilmos").build())
                        .house(new HouseData.Builder().name("house").build())
                        .build();

        PersonData personCreated = personService.addPerson(personData);
        assertNotNull(personCreated.getId());
        assertEquals(personCreated.getLocales().size(), 2);

        personCreated.setDateOfBirth(new FlexibleDate.Builder().year(1065).noMonth().noDay().build());
        personCreated = personService.addPerson(personCreated);

        PersonData child = personService.getPersonById(personCreated.getChildren().get(0).getId());
        assertEquals(child.getName(), "William2");

        List<PersonData> personDataList = personService.getPersonsByName("William2");
        personDataList.get(0).setName("William3");
        personService.addPerson(personDataList.get(0));

        List<PersonData> personList = personService.getPersons();
        assertEquals(2, personList.size());

        personService.deletePerson(child);
        personList = personService.getPersons();
        assertEquals(1, personList.size());
    }
}
