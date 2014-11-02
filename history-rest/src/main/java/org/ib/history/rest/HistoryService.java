package org.ib.history.rest;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.db.neo4j.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Path("/")
public class HistoryService {

    @Autowired
    private PersonService personService;

    @GET
    @Path("/ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "pong";
    }

//    @GET
//    @Path("/test/numbers")
//    @Produces(MediaType.TEXT_PLAIN)
//    public

    @GET
    @Path("/test/country")
    @Produces(MediaType.APPLICATION_JSON)
    public CountryData getTestCountry() {
        return getEngland();
    }

    @GET
    @Path("/test/countries")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CountryData> getTestCountries() {
        return Arrays.asList(getEngland(), getGermany());
    }

    @GET
    @Path("/test/persons")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonData> getTestPersons() {
        return null;
    }


    @GET
    @Path("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonData> getPersons() {
        return personService.getPersonsByName("William");
    }

    @GET
    @Path("/person/name")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getPersonNames() {
        return personService.getPersons().stream().map(PersonData::getName).collect(Collectors.toList());
    }

    @GET
    @Path("/person/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonJson> getPersonJsons() {
        return personService.getPersons().stream()
                .map(person -> new PersonJson(
                        person.getName(),
                        person.getDateOfBirth() != null ? person.getDateOfBirth().getYear() : 0,
                        person.getDateOfDeath() != null ? person.getDateOfDeath().getYear() : 0))
                .collect(Collectors.toList());
    }

    public static class PersonJson {
        private final String name;
        private final int yearOfBirth;
        private final int yearOfDeath;

        public PersonJson(String name, int yearOfBirth, int yearOfDeath) {
            this.name = name;
            this.yearOfBirth = yearOfBirth;
            this.yearOfDeath = yearOfDeath;
        }

        public String getName() {
            return name;
        }

        public int getYearOfBirth() {
            return yearOfBirth;
        }

        public int getYearOfDeath() {
            return yearOfDeath;
        }
    }


    private CountryData getEngland() {
        return new CountryData.Builder()
                .id(12L).name("England")
                .locale("DE", new CountryData.Builder().id(22L).name("England").build())
                .locale("HU", new CountryData.Builder().id(23L).name("Anglia").build())
                .build();
    }

    private CountryData getGermany() {
        return new CountryData.Builder()
                .id(13L).name("Germany")
                .locale("DE", new CountryData.Builder().id(32L).name("Deutschland").build())
                .locale("HU", new CountryData.Builder().id(33L).name("Németország").build())
                .build();
    }
}
