package org.ib.history.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.utils.Neo4jDateFormat;
import org.ib.history.db.neo4j.NeoService;
import org.ib.history.db.neo4j.WikiRelationships;
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
        return createNodeCsv(getWikiPerson(wikiPage));
    }

    private String createNodeCsv(WikiPerson wikiPerson) {
        StringBuilder sb = new StringBuilder("id,name,birth,death\n");

        addRow(sb, wikiPerson);
        addRow(sb, wikiPerson.getFather(), true);
        addRow(sb, wikiPerson.getMother(), true);
        wikiPerson.getSpouses().stream().forEach(s -> addRow(sb, s, true));
        wikiPerson.getIssues().stream().forEach(s -> addRow(sb, s, true));

        return sb.toString();
    }

    @GET
    @Path("/person/{wikiPage}/edges")
    @Produces("text/csv")
    public String edges(@PathParam("wikiPage") String wikiPage) {
        logger.info("edges called for [{}]", wikiPage);
        return createEdgeCsv(getWikiPerson(wikiPage));
    }

    private String createEdgeCsv(WikiPerson wikiPerson) {
        StringBuilder sb = new StringBuilder("source,target,type\n");

        addRow(sb, wikiPerson.getWikiNamedResource(), wikiPerson.getFather(), WikiRelationships.HAS_FATHER);
        addRow(sb, wikiPerson.getWikiNamedResource(), wikiPerson.getMother(), WikiRelationships.HAS_MOTHER);
        addRow(sb, wikiPerson.getFather(), wikiPerson.getMother(), WikiRelationships.IS_SPOUSE_OF);

        wikiPerson.getSpouses().stream().forEach(s -> addRow(sb, wikiPerson.getWikiNamedResource(), s, WikiRelationships.IS_SPOUSE_OF));
        wikiPerson.getIssues().stream().forEach(s -> addRow(sb, wikiPerson.getWikiNamedResource(), s, WikiRelationships.HAS_ISSUE));

        return sb.toString();
    }


    private WikiPerson getWikiPerson(String wikiPage) {
        WikiPerson person;

        Optional<WikiPerson> neoPerson = neoService.getPerson(wikiPage);

        if (!neoPerson.isPresent()) {
            logger.debug("Calling WikiService");
            WikiPerson wikiPerson = wikiService.getPerson(wikiPage);
            logger.trace(wikiPerson);
            person = wikiPerson;
            neoService.save(wikiPerson);
        } else {
            logger.debug("Returning from cache");
            person = neoPerson.get();
        }

        return person;
    }

    private void addRow(StringBuilder sb, WikiPerson wikiPerson) {
        addRow(sb, wikiPerson.getWikiNamedResource(), false);
        sb.append(",\"").append(Neo4jDateFormat.dateWrapperToString(wikiPerson.getDateOfBirth())).append("\"");
        sb.append(",\"").append(Neo4jDateFormat.dateWrapperToString(wikiPerson.getDateOfDeath())).append("\"");
        sb.append("\n");
    }

    private void addRow(StringBuilder sb, WikiNamedResource wikiResource, boolean lineFeed) {
        System.out.println(wikiResource);
        sb.append("\"").append(wikiResource.getLocalPartNoUnderscore()).append("\",\"").append(wikiResource.getDisplayText()).append("\"");
        if (lineFeed) {
            sb.append(",\"\",\"\"\n");
        }
    }

    private void addRow(StringBuilder sb, WikiNamedResource wikiPerson, WikiNamedResource wikiResource, WikiRelationships relType) {
        sb.append("\"");
        sb.append(wikiPerson.getLocalPartNoUnderscore()).append("\",\"");
        sb.append(wikiResource.getLocalPartNoUnderscore()).append("\",\"");
        sb.append(relType).append("\"\n");
    }
}
