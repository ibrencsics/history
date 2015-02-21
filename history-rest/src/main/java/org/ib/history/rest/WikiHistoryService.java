package org.ib.history.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.utils.Neo4jDateFormat;
import org.ib.history.db.neo4j.GenderType;
import org.ib.history.db.neo4j.data.NeoCountry;
import org.ib.history.db.neo4j.data.NeoHouse;
import org.ib.history.db.neo4j.data.NeoPerson;
import org.ib.history.db.neo4j.NeoService;
import org.ib.history.db.neo4j.WikiRelationships;
import org.ib.history.db.neo4j.data.NeoSuccession;
import org.ib.history.rest.data.JsonCountry;
import org.ib.history.rest.data.JsonHouse;
import org.ib.history.rest.data.JsonJob;
import org.ib.history.rest.data.JsonPerson;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Path("/wiki")
public class WikiHistoryService {

    private static Logger logger = LogManager.getLogger(WikiHistoryService.class);

    @Autowired
    private WikiService wikiService;

    @Autowired
    private NeoService neoService;

    @GET
    @Path("/person/{wikiPage}")
    @Produces(MediaType.APPLICATION_JSON)
    public WikiPerson getPerson(@PathParam("wikiPage") String wikiPage) {
        return wikiService.getPerson(wikiPage);
    }

    @GET
    @Path("/person/{wikiPage}/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WikiPerson> getPersonAsList(@PathParam("wikiPage") String wikiPage) {
        return Arrays.asList(getPerson(wikiPage));
    }

    // ---------------------------

    @GET
    @Path("/person/{wikiPage}/details")
    @Produces("application/json")
    public JsonPerson personDetails(@PathParam("wikiPage") String wikiPage) {
        logger.info("node details called for [{}]", wikiPage);
        NeoPerson neoPerson = getNeoPerson(wikiPage);
        logger.trace(neoPerson);

        return getJsonPerson(neoPerson);
    }

    private NeoPerson getNeoPerson(String wikiPage) {
        Optional<NeoPerson> maybeNeoPerson = neoService.getNeoPerson(wikiPage);

        if (!maybeNeoPerson.isPresent()) {
            logger.debug("Calling WikiService");
            WikiPerson wikiPerson = wikiService.getPerson(wikiPage);
            neoService.save(wikiPerson);
            maybeNeoPerson = neoService.getNeoPerson(wikiPage);
        } else {
            logger.debug("Returning from cache");
        }

        return maybeNeoPerson.get();
    }

    private JsonPerson getJsonPerson(NeoPerson neoPerson) {
        JsonPerson jsonPerson = new JsonPerson();

        jsonPerson.setWikiPage(neoPerson.getWikiPage());
        jsonPerson.setName(neoPerson.getName());
        neoPerson.getDateOfBirth().ifPresent(date -> jsonPerson.setDateOfBirth(Neo4jDateFormat.dateWrapperToString(date)));
        neoPerson.getDateOfDeath().ifPresent(date -> jsonPerson.setDateOfDeath(Neo4jDateFormat.dateWrapperToString(date)));
        neoPerson.getGender().ifPresent(gender -> jsonPerson.setGender(gender.name()));

        neoPerson.getFather().ifPresent(father -> jsonPerson.setFather(getJsonPerson(father)));
        neoPerson.getMother().ifPresent(mother -> jsonPerson.setMother(getJsonPerson(mother)));

        if (!neoPerson.getHouses().isEmpty())
            jsonPerson.setHouses(neoPerson.getHouses().stream().map(house -> getJsonHouse(house)).collect(Collectors.toList()));

        if (!neoPerson.getSpouses().isEmpty()) {
            jsonPerson.setSpouses(neoPerson.getSpouses().stream().map(spouse -> getJsonPerson(spouse)).collect(Collectors.toList()));
        }

        if (!neoPerson.getIssues().isEmpty()) {
            jsonPerson.setIssues(neoPerson.getIssues().stream().map(issue -> getJsonPerson(issue)).collect(Collectors.toList()));
        }

        if (!neoPerson.getSuccessions().isEmpty()) {
            jsonPerson.setJobs(neoPerson.getSuccessions().stream().map(succ -> getJsonJob(succ)).collect(Collectors.toList()));
        }

        return jsonPerson;
    }

    private JsonHouse getJsonHouse(NeoHouse neoHouse) {
        JsonHouse jsonHouse = new JsonHouse();
        jsonHouse.setWikiPage(neoHouse.getWikiPage());
        jsonHouse.setName(neoHouse.getName());
        return jsonHouse;
    }

    private JsonJob getJsonJob(NeoSuccession neoSucc) {
        JsonJob jsonJob = new JsonJob();
        jsonJob.setTitle(neoSucc.getTitle());
        neoSucc.getFrom().ifPresent(date -> jsonJob.setFrom(Neo4jDateFormat.dateWrapperToString(date)));
        neoSucc.getTo().ifPresent(date -> jsonJob.setTo(Neo4jDateFormat.dateWrapperToString(date)));
        neoSucc.getPredecessor().ifPresent(pred -> jsonJob.setPredecessor(getJsonPerson(pred)));
        neoSucc.getSuccessor().ifPresent(succ -> jsonJob.setSuccecessor(getJsonPerson(succ)));
        jsonJob.setCountries(neoSucc.getCountries().stream().map(c -> getJsonCountry(c)).collect(Collectors.toList()));
        return jsonJob;
    }

    private JsonCountry getJsonCountry(NeoCountry neoCountry) {
        JsonCountry jsonCountry = new JsonCountry();
        jsonCountry.setWikiPage(neoCountry.getWikiPage());
        jsonCountry.setName(neoCountry.getName());
        return jsonCountry;
    }

    @GET
    @Path("/person/{wikiPage}/delete")
    public Response deletePerson(@PathParam("wikiPage") String wikiPage) {
        neoService.delete(wikiPage);
        return Response.noContent().build();
    }
}