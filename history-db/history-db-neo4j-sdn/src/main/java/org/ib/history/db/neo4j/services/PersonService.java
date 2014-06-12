package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;

import java.util.List;

public interface PersonService {
    List<PersonData> getPersons();
    List<PersonData> getPersonsByName(String name);
    PersonData getPersonById(Long id);
    PersonData addPerson(PersonData personData);
    void deletePerson(PersonData personData);
}