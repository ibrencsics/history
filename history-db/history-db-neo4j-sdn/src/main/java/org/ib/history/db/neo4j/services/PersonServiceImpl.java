package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulesData;
import org.ib.history.commons.data.SpouseData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepo;

    @Override
    public List<PersonData> getPersons() {
        return getPersons(personRepo.getPersons());
    }

    @Override
    public List<PersonData> getPersons(int start, int length) {
        return getPersons(personRepo.getPersons(start, length));
    }

    private List<PersonData> getPersons(List<Person> personList) {
        List<PersonData> personDataList = new ArrayList<>();

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
    public List<PersonData> getPersonsById(List<PersonData> personsOnlyIds) {
        List<PersonData> personsFull = new ArrayList<>();

        for (PersonData personIdOnly : personsOnlyIds) {
            personsFull.add( DataTransformer.transform( personRepo.findOne(personIdOnly.getId()) ) );
        }

        return personsFull;
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

    @Override
    public PersonData setParents(PersonData personData, List<PersonData> parents) {
        personRepo.deleteParents(personData.getId());
        for (PersonData parent : parents) {
            personRepo.addParent(personData.getId(), parent.getId());
        }
        return DataTransformer.transform(personRepo.findOne(personData.getId()));
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

    // custom

    @Override
    public void changeName(Long id, String name) {
        personRepo.changeName(id, name);
    }

    @Override
    public void addParent(Long id, Long parentId) {
        personRepo.addParent(id, parentId);
    }

    @Override
    public void deleteParent(Long id, Long parentId) {
        personRepo.deleteParent(id, parentId);
    }

    @Override
    public void deleteParents(Long id) {
        personRepo.deleteParents(id);
    }

    @Override
    public void addHouse(Long id, Long houseId) {
        personRepo.addHouse(id, houseId);
    }

    @Override
    public void deleteHouses(Long id) {
        personRepo.deleteHouses(id);
    }

    @Override
    public void addSpouse(SpouseData spouseData) {
        personRepo.addSpouse(DataTransformer.transform(spouseData));
    }

    @Override
    public void deleteSpouses(Long id) {
        personRepo.deleteSpouses(id);
    }

    @Override
    public void addRules(RulesData rulesData) {
        personRepo.addRules(DataTransformer.transform(rulesData));
    }

    @Override
    public void deleteRules(Long id) {
        personRepo.deleteRules(id);
    }

    @Override
    public void setPope(Long id, Long popeId) {
        personRepo.setPope(id, popeId);
    }

    @Override
    public void deletePope(Long id) {
        personRepo.deletePope(id);
    }

    // advanced

    @Override
    public List<RulesData> getRulers(String countryName) {
        List<RulesData> rulerDatas = new ArrayList<>();

        List<PersonRepository.Ruler> rulers = personRepo.getRulers(countryName);
//        rulerDatas = rulers.stream().map(r -> DataTransformer.transform(r.rules())).collect(Collectors.toList());

        for (PersonRepository.Ruler ruler : rulers) {
            RulesData rulesData = DataTransformer.transform(ruler.rules());
            rulesData.getPerson().setName(ruler.person().getName());
            rulesData.getCountry().setName(ruler.country().getName());
            rulerDatas.add(rulesData);
        }

        return rulerDatas;
    }
}
