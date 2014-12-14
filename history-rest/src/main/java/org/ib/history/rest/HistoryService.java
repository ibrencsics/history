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
    }

    @GET
    @Path("/ruler/json")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RulerJson> getRulerJsons() {
        return personService.getRulers("England").stream()
                .map(r -> new RulerJson(
                        r.getPerson().getName(),
                        r.getNumber(),
                        r.getTitle(),
                        r.getFrom().getYear(),
                        r.getTo().getYear(),
                        r.getCountry().getName()))
                .collect(Collectors.toList());
    }


    public static class RulerJson {
        private final String name;
        private final int number;
        private final String title;
        private final int startYear;
        private final int endYear;
        private final String countryName;

        public RulerJson(String name, int number, String title, int startYear, int endYear, String countryName) {
            this.name = name;
            this.number = number;
            this.title = title;
            this.startYear = startYear;
            this.endYear = endYear;
            this.countryName = countryName;
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