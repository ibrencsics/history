package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.domain.House;
import org.ib.history.db.neo4j.domain.Person;
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
public class PersonRepositoryTest {

    @Autowired
    CountryRepository countryRepo;

    @Autowired
    HouseRepository houseRepo;

    @Autowired PersonRepository personRepo;

    @Test
    public void test_basic() {
        Person person = basicPerson();
        Person personCreated = personRepo.save(person);

        assertNotNull(personCreated);
        assertNotNull(personCreated.getId());
        assertEquals(person.getName(), personCreated.getName());
        assertEquals(person.getAlias(), personCreated.getAlias());
    }

    @Test
    public void test_basic_with_readback() {
        Person person = basicPerson();
        personRepo.save(person);

        List<Person> persons = personRepo.getPersons();
        assertNotNull(persons);
        assertEquals(1, persons.size());
    }


    @Test
    public void test_with_house() {
        House house = createHouse();
        Person person = basicPerson();
        person.addHouse(house);
        Person personCreated = personRepo.save(person);

        assertNotNull(personCreated);
        assertNotNull(personCreated.getId());
        assertEquals(person.getName(), personCreated.getName());
        assertEquals(person.getAlias(), personCreated.getAlias());
        assertNotNull(personCreated.getHouses());
        assertEquals(1, personCreated.getHouses().size());

        personCreated.cleanHouses();
        personCreated = personRepo.save(personCreated);
        assertEquals(0, personCreated.getHouses().size());
    }

    @Test
    public void test_change_name() {
        House house = createHouse();
        Person person = basicPerson();
        person.addHouse(house);
        Person personCreated = personRepo.save(person);

        personRepo.changeName(personCreated.getId(), "Willy");

        List<Person> persons = personRepo.getPersons();
        assertNotNull(persons);
        assertEquals(1, persons.size());

        Person personChanged = persons.get(0);
        assertEquals("Willy", personChanged.getName());
        assertEquals(1, personChanged.getHouses().size());
    }


    private Country createCountry() {
        Country country = new Country();
        country.setName("England");
        country.setDefaultLocale(true);
        return countryRepo.save(country);
    }

    private House createHouse() {
        House house = new House();
        house.setName("Normandy");
        house.setDefaultLocale(true);
        return houseRepo.save(house);
    }

    private Person basicPerson() {
        Person person =  new Person(null, "William", "The Conquerer");
        person.setDefaultLocale(true);
        return person;
    }
}
