package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Ruler;
import org.ib.history.db.neo4j.repositories.PersonRepository;
import org.ib.history.db.neo4j.repositories.RulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepo;

    @Override
    public List<PersonData> getPersons() {
        List<PersonData> personDataList = new ArrayList<>();

        List<Person> personList = personRepo.getAllPersons();
        for (Person person : personList) {
            personDataList.add(DataTransformer.transform(person));
        }

        return personDataList;
    }

    @Override
    public List<PersonData> getPersonsByName(String name) {
        return convertPersonList(personRepo.getPersonsByName(name));
    }

    @Override
    public PersonData getPersonById(Long id) {
        return DataTransformer.transform(personRepo.findOne(id));
    }

    @Override
    public List<PersonData> getPersonsByPattern(String pattern) {
        List<PersonData> personDataList = new ArrayList<>();

        List<Person> personList = personRepo.getPersonsByPattern("(?i).*" + pattern + ".*");
        for (Person person : personList) {
            personDataList.add(DataTransformer.transform(person));
        }

        return personDataList;
    }

    @Override
    public PersonData getPersonByRuler(RulerData rulerData) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PersonData addPerson(PersonData personData) {
        Person person = DataTransformer.transform(personData);
        for (Person.Translation<Person> translation : person.getLocales()) {
            personRepo.save(translation.getTranslation());
        }

        Person personCreated = personRepo.save(person);
        return DataTransformer.transform(personCreated);
    }

    private List<PersonData> convertPersonList(List<Person> personList) {
        List<PersonData> personDataList = new ArrayList<>();

        for (Person person : personList) {
            personDataList.add(DataTransformer.transform(person));
        }

        return personDataList;
    }

    @Override
    public void deletePerson(PersonData personData) {
        personRepo.delete(personData.getId());
    }
}
