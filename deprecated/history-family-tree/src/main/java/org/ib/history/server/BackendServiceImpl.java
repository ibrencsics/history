package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.commons.data.*;
import org.ib.history.db.neo4j.services.CountryService;
import org.ib.history.db.neo4j.services.HouseService;
import org.ib.history.db.neo4j.services.PersonService;
import org.ib.history.db.neo4j.services.PopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("backendService")
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

    private static Logger log = LoggerFactory.getLogger(BackendServiceImpl.class);

    @Autowired
    CountryService countryService;
    @Autowired
    PersonService personService;
    @Autowired
    HouseService houseService;
    @Autowired
    PopeService popeService;


    @Override
    public List<CountryData> getCountries() {
        return countryService.getCountries();
    }

    @Override
    public List<CountryData> getCountries(int start, int length) {
        return countryService.getCountries(start, length);
    }

    @Override
    public List<CountryData> getCountriesByPattern(String pattern) {
        return countryService.getCountriesByPattern(pattern);
    }

    @Override
    public List<CountryData> getCountriesByIds(List<CountryData> countriesOnlyIds) {
        return countryService.getCountriesById(countriesOnlyIds);
    }

    @Override
    public void addCountry(CountryData country) {
        countryService.addCountry(country);
    }

    @Override
    public void deleteCountry(CountryData country) {
        countryService.deleteCountry(country);
    }


    @Override
    public List<HouseData> getHouses() {
        return houseService.getHouses();
    }

    @Override
    public List<HouseData> getHouses(int start, int length) {
        return houseService.getHouses(start, length);
    }

    @Override
    public List<HouseData> getHousesByPattern(String pattern) {
        return houseService.getHousesByPattern(pattern);
    }

    @Override
    public List<HouseData> getHousesByIds(List<HouseData> housesOnlyIds) {
        return houseService.getHousesById(housesOnlyIds);
    }

    @Override
    public void addHouse(HouseData house) {
        houseService.addHouse(house);
    }

    @Override
    public void deleteHouse(HouseData house) {
        houseService.deleteHouse(house);
    }


    @Override
    public List<PersonData> getPersons() {
        List<PersonData> persons = personService.getPersons();
        return persons;
    }

    @Override
    public List<PersonData> getPersons(int start, int length) {
        return personService.getPersons(start, length);
    }

    @Override
    public List<PersonData> getPersonsByPattern(String pattern) {
        return personService.getPersonsByPattern(pattern);
    }

    @Override
    public List<PersonData> getPersonsByIds(List<PersonData> personsOnlyIds) {
        return personService.getPersonsById(personsOnlyIds);
    }

    @Override
    public void addPerson(PersonData person) {
        log.debug(person.toString());
        personService.addPerson(person);
    }

    @Override
    public void deletePerson(PersonData person) {
        personService.deletePerson(person);
    }

    @Override
    public void setParents(PersonData person, List<PersonData> parents) {
        personService.deleteParents(person.getId());
        for (PersonData parent : parents) {
            personService.addParent(person.getId(), parent.getId());
        }
    }

    @Override
    public void setHouses(PersonData person, List<HouseData> houses) {
        personService.deleteHouses(person.getId());
        for (HouseData house : houses) {
            personService.addHouse(person.getId(), house.getId());
        }
    }

    @Override
    public void setSpouses(PersonData person, List<SpouseData> spouses) {
        personService.deleteSpouses(person.getId());
        for (SpouseData spouse : spouses) {
            personService.addSpouse(spouse);
        }
    }

    @Override
    public void setRules(PersonData person, List<RulesData> rules) {
        personService.deleteRules(person.getId());
        for (RulesData rule : rules) {
            personService.addRules(rule);
        }
    }

    @Override
    public void setPope(PersonData person, PopeData pope) {
        personService.deletePope(person.getId());
        personService.setPope(person.getId(), pope.getId());
    }


    @Override
    public List<PopeData> getPopes() {
        return popeService.getPopes();
    }

    @Override
    public List<PopeData> getPopes(int start, int length) {
        return popeService.getPopes(start, length);
    }

    @Override
    public List<PopeData> getPopesByPattern(String pattern) {
        return popeService.getPopesByPattern(pattern);
    }

    @Override
    public PopeData getPopeById(PopeData popeOnlyId) {
        return popeService.getPopeById(popeOnlyId);
    }

    @Override
    public void addPope(PopeData popeData) {
        popeService.addPope(popeData);
    }

    @Override
    public void deletePope(PopeData popeData) {
        popeService.deletePope(popeData);
    }
}
