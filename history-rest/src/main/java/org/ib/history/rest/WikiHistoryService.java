package org.ib.history.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.db.neo4j.NeoService;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Path("/person/{wikiPage}/nodes")
    @Produces("text/csv")
    public String nodes(@PathParam("wikiPage") String wikiPage) {
        logger.info("nodes called for [{}]", wikiPage);
        WikiPerson person;

        Optional<WikiPerson> neoPerson = neoService.getPerson(wikiPage);

        if (!neoPerson.isPresent()) {
            logger.debug("Calling WikiService");
            WikiPerson wikiPerson = wikiService.getPerson(wikiPage);
            person = wikiPerson;
            neoService.save(wikiPerson);
        } else {
            logger.debug("Returning from cache");
            person = neoPerson.get();
        }

        return createNodeCsv(person);
    }

    private String createNodeCsv(WikiPerson wikiPerson) {
        StringBuilder sb = new StringBuilder("wikiPage,name,dateOfBirth,dateOfDeath\n");


        return sb.toString();
    }
}
