package org.ib.history.rest;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Service
@Path("/")
public class HistoryService {

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CountryData getTestCountry() {
        return getEngland();
    }

    @GET
    @Path("/test/countries")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CountryData> getTestCountries() {
        return Arrays.asList(getEngland(), getGermany());
    }

    @GET
    @Path("/test/persons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonData> getTestPersons() {
        return null;
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
