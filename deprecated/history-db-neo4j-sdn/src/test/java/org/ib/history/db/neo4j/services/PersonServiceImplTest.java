package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.*;
import org.ib.history.db.neo4j.domain.Rules;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class PersonServiceImplTest {

    @Autowired
    HouseService houseService;

    @Autowired
    CountryService countryService;

    @Autowired
    PersonService personService;


    @Test
    public void test_add() {
        PersonData person = testPerson().build();
        PersonData personCreated = personService.addPerson(person);
        assertNotNull(personCreated);
        assertNotNull(personCreated.getId());
        assertEquals(2, personCreated.getLocales().size());
        for (PersonData locale : personCreated.getLocales().values()) {
            assertNotNull(locale.getId());
        }
    }

    @Test
    public void test_custom_change_name() {
        PersonData person = testPerson().build();
        PersonData personCreated = personService.addPerson(person);
        assertNotNull(personCreated);
        assertNotNull(personCreated.getId());
        assertEquals("William", personCreated.getName());

        personService.changeName(personCreated.getId(), "Willy");

        List<PersonData> persons = personService.getPersons();
        assertEquals(1, persons.size());
        assertEquals("Willy", persons.get(0).getName());
    }

    @Test
    public void test_with_parents() {
        PersonData.Builder personDataBuilder = testPerson();

        PersonData parent1 = new PersonData.Builder().name("Parent1").build();
        PersonData parent1Created = personService.addPerson(parent1);
        PersonData parent2 = new PersonData.Builder().name("Parent2").build();
        PersonData parent2Created = personService.addPerson(parent2);

        PersonData personCreated = personService.addPerson(personDataBuilder.parent(parent1Created).parent(parent2Created).build());
        assertNotNull(personCreated.getParents());
        assertEquals(2, personCreated.getParents().size());
    }

    @Test
    public void test_custom_add_delete_parents() {
        PersonData personData = testPerson().build();
        personData = personService.addPerson(personData);

        PersonData parent1 = new PersonData.Builder().name("Parent1").build();
        PersonData parent2 = new PersonData.Builder().name("Parent2").build();
        PersonData parent3 = new PersonData.Builder().name("Parent3").build();
        parent1 = personService.addPerson(parent1);
        parent2 = personService.addPerson(parent2);
        parent3 = personService.addPerson(parent3);
        assertEquals(0, personData.getParents().size());

        personService.addParent(personData.getId(), parent1.getId());
        personService.addParent(personData.getId(), parent2.getId());
        personService.addParent(personData.getId(), parent3.getId());
        personData = personService.getPersonById(personData.getId());
        assertEquals(3, personData.getParents().size());

        personService.deleteParent(personData.getId(), parent2.getId());
        personData = personService.getPersonById(personData.getId());
        assertEquals(2, personData.getParents().size());

        personService.deleteParents(personData.getId());
        personData = personService.getPersonById(personData.getId());
        assertEquals(0, personData.getParents().size());
    }

    @Test
    public void test_with_house() {
        HouseData house = testHouse();
        HouseData houseCreated = houseService.addHouse(house);

        PersonData person = testPerson().house(houseCreated).build();
        PersonData personCreated = personService.addPerson(person);
        assertNotNull(personCreated);
        assertNotNull(personCreated.getId());
        assertNotNull(personCreated.getHouses());
        assertEquals(1, personCreated.getHouses().size());

        assertEquals(1, houseService.getHouses().size());

        assertEquals(houseCreated.getId(), personCreated.getHouses().iterator().next().getId());
    }



    @Test
    public void test_with_spouse() {
        PersonData.Builder personDataBuilder = testPerson();

        PersonData spouseCreated = personService.addPerson(new PersonData.Builder().name("Spouse").build());
        SpouseData spouseData = new SpouseData.Builder()
                .from(new FlexibleDate.Builder().year(1022).noMonth().noDay().build())
                .to(new FlexibleDate.Builder().year(1042).noMonth().noDay().build())
                .build();
    }

    @Test
    public void test_custom_with_spouse() {
        PersonData personData1 = new PersonData.Builder().name("person1").build();
        PersonData personData2 = new PersonData.Builder().name("person2").build();

        personData1 = personService.addPerson(personData1);
        personData2 = personService.addPerson(personData2);

        SpouseData spouseData = new SpouseData.Builder()
                .person1(personData1).person2(personData2)
                .from(new FlexibleDate.Builder().year(1111).noMonth().noDay().build())
                .to(new FlexibleDate.Builder().year(2222).noMonth().noDay().build())
                .build();

        personService.addSpouse(spouseData);

        // verify
        PersonData personData = personService.getPersonById(personData1.getId());
        assertEquals(1, personData.getSpouses().size());
        assertEquals(1111, personData.getSpouses().iterator().next().getFrom().getYear());
    }

    @Test
    public void test_custom_with_rules() {
        PersonData personData = new PersonData.Builder().name("person").build();
        CountryData countryData = new CountryData.Builder().name("country").build();

        personData = personService.addPerson(personData);
        countryData = countryService.addCountry(countryData);

        RulesData rulesData = new RulesData.Builder()
                .person(personData)
                .country(countryData)
                .title("king")
                .number(1)
                .from(new FlexibleDate.Builder().year(1111).noMonth().noDay().build())
                .to(new FlexibleDate.Builder().year(2222).noMonth().noDay().build())
                .build();

        personService.addRules(rulesData);

        // verify
        personData = personService.getPersonById(personData.getId());
        assertEquals(1, personData.getRules().size());
        assertEquals(1111, personData.getRules().iterator().next().getFrom().getYear());
    }


//    @Test
//    @Rollback(false)
    public void test() {

        PersonData personData =
                new PersonData.Builder().name("William")
                        .dateOfDeath(new FlexibleDate.Builder().year(1087).noMonth().noDay().build())
                        .parent(new PersonData.Builder().name("William2").build())
                        .locale("DE", new PersonData.Builder().name("Wilhelm").build())
                        .locale("HU", new PersonData.Builder().name("Vilmos").build())
                        .house(new HouseData.Builder().name("house").build())
                        .build();

        PersonData personCreated = personService.addPerson(personData);
        assertNotNull(personCreated.getId());
        assertEquals(personCreated.getLocales().size(), 2);

        personCreated.setDateOfBirth(new FlexibleDate.Builder().year(1065).noMonth().noDay().build());
        personCreated = personService.addPerson(personCreated);

        PersonData child = personService.getPersonById(personCreated.getParents().get(0).getId());
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



    private HouseData testHouse() {
        return new HouseData.Builder().name("Normandy").build();
    }

    private CountryData testCountry() {
        return new CountryData.Builder().name("England").build();
    }

    private PersonData.Builder testPerson() {
        return new PersonData.Builder()
                .name("William")
                .dateOfDeath(new FlexibleDate.Builder().year(1087).noMonth().noDay().build())
                .locale("DE", new PersonData.Builder().name("Wilhelm").build())
                .locale("HU", new PersonData.Builder().name("Vilmos").build());
    }
}
