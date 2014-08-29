package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;

import java.util.List;

public interface PersonService {
    List<PersonData> getPersons();
    List<PersonData> getPersonsByName(String name);
    PersonData getPersonById(Long id);
    List<PersonData> getPersonsByPattern(String pattern);
    PersonData getPersonByRuler(RulerData rulerData);
    List<PersonData> getPersonsById(List<PersonData> personsOnlyIds);
    PersonData addPerson(PersonData personData);
    void deletePerson(PersonData personData);
}
