package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.db.neo4j.services.CountryService;
import org.ib.history.db.neo4j.services.HouseService;
import org.ib.history.db.neo4j.services.PersonService;
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


    @Override
    public List<CountryData> getCountries(String locale) {
        return countryService.getCountries();
    }

    @Override
    public List<CountryData> getCountriesByPattern(String pattern) {
        return countryService.getCountriesByPattern(pattern);
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
    public List<HouseData> getHousesByPattern(String pattern) {
        return houseService.getHousesByPattern(pattern);
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
        log.debug(persons.toString());
        return persons;
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
}
