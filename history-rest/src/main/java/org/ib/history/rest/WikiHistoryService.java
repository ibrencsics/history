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
    @Path("/person/{wikiPage}/nodes")
    @Produces("text/csv")
    public String nodes(@PathParam("wikiPage") String wikiPage) {
        logger.info("nodes called for [{}]", wikiPage);
//        return createNodeCsv(getWikiPerson(wikiPage));
        NeoPerson neoPerson = getNeoPerson(wikiPage);
        logger.trace(neoPerson);
        return createNodeCsv(neoPerson);
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

    private String createNodeCsv(NeoPerson neoPerson) {
        StringBuilder sb = new StringBuilder("id,name,birth,death,gender\n");
        addRow(sb, neoPerson);
        addRow(sb, neoPerson.getFather().get());
        addRow(sb, neoPerson.getMother().get());
        neoPerson.getSpouses().stream().forEach(s -> addRow(sb, s));
        neoPerson.getIssues().stream().forEach(s -> addRow(sb, s));

        return sb.toString();
    }

    @GET
    @Path("/person/{wikiPage}/details")
    @Produces("application/json")
    public JsonPerson personDetails(@PathParam("wikiPage") String wikiPage) {
        logger.info("node details called for [{}]", wikiPage);
        NeoPerson neoPerson = getNeoPerson(wikiPage);
        logger.trace(neoPerson);

        return getJsonPerson(neoPerson);
    }

    private JsonPerson getJsonPerson(NeoPerson neoPerson) {
        JsonPerson jsonPerson = new JsonPerson();

        jsonPerson.setWikiPage(neoPerson.getWikiPage());
        jsonPerson.setName(neoPerson.getName());
        neoPerson.getDateOfBirth().ifPresent(date -> jsonPerson.setDateOfBirth(Neo4jDateFormat.serialize(date)));
        neoPerson.getDateOfDeath().ifPresent(date -> jsonPerson.setDateOfDeath(Neo4jDateFormat.serialize(date)));

        neoPerson.getFather().ifPresent(father -> jsonPerson.setFather(getJsonPerson(father)));
        neoPerson.getMother().ifPresent(mother -> jsonPerson.setMother(getJsonPerson(mother)));

        if (!neoPerson.getHouses().isEmpty())
            jsonPerson.setHouses(neoPerson.getHouses().stream().map(house -> getJsonHouse(house)).collect(Collectors.toList()));

        if (!neoPerson.getSpouses().isEmpty()) {
            jsonPerson.setSpouses(neoPerson.getSpouses().stream().map(spouse -> getJsonPerson(spouse)).collect(Collectors.toList()));
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
        neoSucc.getFrom().ifPresent(date -> jsonJob.setFrom(Neo4jDateFormat.serialize(date)));
        neoSucc.getTo().ifPresent(date -> jsonJob.setTo(Neo4jDateFormat.serialize(date)));
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

    private void addRow(StringBuilder sb, WikiPerson wikiPerson) {
        addRow(sb, wikiPerson.getWikiNamedResource(), false);
        sb.append(",\"").append(Neo4jDateFormat.dateWrapperToString(wikiPerson.getDateOfBirth())).append("\"");
        sb.append(",\"").append(Neo4jDateFormat.dateWrapperToString(wikiPerson.getDateOfDeath())).append("\"");
        sb.append("\n");
    }

    private void addRow(StringBuilder sb, NeoPerson neoPerson) {
        String q = "\"", sep = ",\"", br = "\n";
        sb.append(q).append(neoPerson.getWikiPage()).append(q)
                .append(sep).append(neoPerson.getName()).append(q)
                .append(sep).append(neoPerson.getDateOfBirth().isPresent() ? Neo4jDateFormat.dateWrapperToString(neoPerson.getDateOfBirth().get()) : "").append(q)
                .append(sep).append(neoPerson.getDateOfDeath().isPresent() ? Neo4jDateFormat.dateWrapperToString(neoPerson.getDateOfDeath().get()) : "").append(q)
                .append(sep).append(neoPerson.getGender().orElse(GenderType.UNKNOWN).name()).append(q)
                .append(br);
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
