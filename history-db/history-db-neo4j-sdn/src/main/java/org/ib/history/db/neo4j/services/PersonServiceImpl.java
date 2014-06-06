package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepo;

    @Override
    public List<PersonData> getPersons() {
        return null;
    }

    @Override
    public PersonData addPerson(PersonData personData) {
        Person person = DataTransformer.transform(personData);
        for (Person.Translation<Person> translation : person.getLocales()) {
            personRepo.save(translation.getTranslation());
        }

        for (PersonData child : personData.getChildren()) {
            PersonData childData = addPerson(child);
            // TODO: replace the children list with the created ones
        }

        Person personCreated = personRepo.save(person);
        return DataTransformer.transform(personCreated);
    }
}
