package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulesData;
import org.ib.history.commons.data.SpouseData;

import java.util.List;

public interface PersonService {
    List<PersonData> getPersons();
    List<PersonData> getPersons(int start, int length);
    List<PersonData> getPersonsByName(String name);
    PersonData getPersonById(Long id);
    List<PersonData> getPersonsByPattern(String pattern);
    List<PersonData> getPersonsById(List<PersonData> personsOnlyIds);

    PersonData addPerson(PersonData personData);
    PersonData setParents(PersonData personData, List<PersonData> parents);

    void deletePerson(PersonData personData);

    void changeName(Long id, String name);
    void addParent(Long id, Long parentId);
    void deleteParent(Long id, Long parentId);
    void deleteParents(Long id);
    void addHouse(Long id, Long houseId);
    void deleteHouses(Long id);
    void addSpouse(SpouseData spouseData);
    void deleteSpouses(Long id);
    void addRules(RulesData rulesData);
    void deleteRules(Long id);
    void setPope(Long id, Long popeId);
    void deletePope(Long id);

    List<RulesData> getRulers(String countryName);
}
