package org.ib.history.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.ib.history.client.BackendService;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PersonData;
import org.ib.history.db.neo4j.Neo4jCountryService;
import org.ib.history.db.neo4j.services.CountryService;
import org.ib.history.db.neo4j.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("backendService")
public class BackendServiceImpl extends RemoteServiceServlet implements BackendService {

    private static Logger log = LoggerFactory.getLogger(BackendServiceImpl.class);

    @Autowired
    CountryService countryService;
    @Autowired
    PersonService personService;

//    Neo4jCountryService neo4jCountryService;

    @Override
    public List<CountryData> getCountries(String locale) {
        return countryService.getCountries();
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
    public List<PersonData> getPersons() {
//        List<PersonData> ret = new ArrayList<>();
//        ret.add(new PersonData.Builder().name("dd")
//                .dateOfBirth(new FlexibleDate.Builder().year(1234).noMonth().noDay().build())
//                .dateOfDeath(new FlexibleDate.Builder().year(1345).noMonth().noDay().build())
//                .build());
//        return ret;
        List<PersonData> persons = personService.getPersons();
        log.debug(persons.toString());
        return persons;
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
