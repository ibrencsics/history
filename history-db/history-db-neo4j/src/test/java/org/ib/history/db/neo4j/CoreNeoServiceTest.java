package org.ib.history.db.neo4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.db.neo4j.data.NeoBaseData;
import org.ib.history.db.neo4j.data.NeoCountry;
import org.ib.history.db.neo4j.data.NeoHouse;
import org.ib.history.db.neo4j.data.NeoPerson;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.ib.history.wiki.domain.WikiSuccession;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CoreNeoServiceTest {

    private static Logger logger = LogManager.getLogger(CoreNeoServiceTest.class);

    private GraphDatabaseService graphDb;
    private NeoService neoService;

    @Before
    public void prepareTestDatabase() {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
//        graphDb = new TestGraphDatabaseFactory().newEmbeddedDatabase("/opt/app-data/history-neo4j-test");
        neoService = new CoreNeoService(graphDb);
    }

    @After
    public void destroyTestDatabase() {
        graphDb.shutdown();
    }

    @Test
    public void testSave() {
        logger.debug("Test testSave");

        neoService.save(person());
        assertThat(neoService.countOf(WikiLabels.PERSON), is(9));
        assertThat(neoService.countOf(WikiLabels.HOUSE), is(1));
        assertThat(neoService.countOf(WikiLabels.COUNTRY), is(2));
    }

    @Test
    public void testSaveTwice() {
        logger.debug("Test testSaveTwice");

        neoService.save(person());
        assertThat(neoService.countOf(WikiLabels.PERSON), is(9));
        assertThat(neoService.countOf(WikiLabels.HOUSE), is(1));
        assertThat(neoService.countOf(WikiLabels.COUNTRY), is(2));

        neoService.save(person());
        assertThat(neoService.countOf(WikiLabels.PERSON), is(9));
        assertThat(neoService.countOf(WikiLabels.HOUSE), is(1));
        assertThat(neoService.countOf(WikiLabels.COUNTRY), is(2));
    }

    @Test
    public void testSaveAndGetPerson() {
        logger.debug("Test testSaveAndGetPerson");

        WikiPerson personIn = person();
        neoService.save(personIn);
        Optional<NeoPerson> maybePersonOut = neoService.getNeoPerson(personIn.getWikiPage().getLocalPartNoUnderscore());

        if (maybePersonOut.isPresent()) {
            NeoPerson personOut = maybePersonOut.get();
            assertFullNeoPerson(personOut, personIn, Optional.empty());
        } else {
            fail("No WikiPerson found");
        }
    }

    @Test
    public void testBaseAfterFull() {
        logger.debug("Test testBaseAfterFull");

        Optional<NeoPerson> maybePersonOut;

        WikiPerson personIn = person();

        // save Full
        neoService.save(personIn);
        maybePersonOut = neoService.getNeoPerson(personIn.getWikiPage().getLocalPartNoUnderscore());
        assertTrue(maybePersonOut.isPresent());
        assertFullNeoPerson(maybePersonOut.get(), personIn, Optional.empty());

        // save Base, ensure there is no change
        neoService.save(personBase());
        maybePersonOut = neoService.getNeoPerson(personIn.getWikiPage().getLocalPartNoUnderscore());
        assertTrue(maybePersonOut.isPresent());
        assertFullNeoPerson(maybePersonOut.get(), personIn, Optional.empty());
    }

    @Test
    public void testChild() {
        logger.debug("Test testChild");

        Optional<NeoPerson> maybePersonOut;

        WikiPerson personIn = person();
        WikiPerson childIn = child();

        neoService.save(personIn);
        neoService.save(childIn);

        maybePersonOut = neoService.getNeoPerson(personIn.getWikiPage().getLocalPartNoUnderscore());
        NeoPerson personOut = maybePersonOut.get();

        // check if the parent gender has been set
        assertThat(personOut.getGender(), is(Optional.of(GenderType.MALE)));

        // check if the child got Full
        NeoPerson childOut = personOut.getIssues().stream()
                .filter(issue -> issue.getWikiPage().equals(childIn.getWikiPage().getLocalPartNoUnderscore()))
                .findAny().get();

        assertBaseNeoBaseData(childOut, new WikiNamedResource(childIn.getWikiPage().getLocalPartNoUnderscore(), childIn.getName()));
        assertThat(childOut.getDateOfBirth().get(), is(childIn.getDateOfBirth()));
        assertThat(childOut.getDateOfDeath().get(), is(childIn.getDateOfDeath()));

        // check if the child lists are not populated
        assertThat(childOut.getFather(), is(Optional.empty()));
        assertThat(childOut.getMother(), is(Optional.empty()));
        assertThat(childOut.getSpouses(), hasSize(0));
        assertThat(childOut.getIssues(), hasSize(0));
        assertThat(childOut.getHouses(), hasSize(0));
        assertThat(childOut.getSuccessions(), hasSize(0));
    }


    private <T> List<String> toStringList(List<T> objectList, Function<? super T, String> function) {
        return objectList.stream().map(w -> function.apply(w)).collect(Collectors.toList());
    }

    private void assertBaseNeoPersonList(List<NeoPerson> outList, List<WikiNamedResource> inList) {
        assertThat(outList, hasSize(inList.size()));
        outList.stream().forEach(personOut -> {
            Optional<WikiNamedResource> maybePersonIn = inList.stream()
                    .filter(spouseIn -> spouseIn.getLocalPart().equals(personOut.getWikiPage()))
                    .findAny();
            assertTrue(maybePersonIn.isPresent());
            assertBaseNeoPerson(personOut, maybePersonIn.get(), Optional.empty());
        });
    }

    private void assertBaseNeoBaseDataList(List<? extends NeoBaseData> outList, List<WikiNamedResource> inList) {
        assertThat(outList, hasSize(inList.size()));
        outList.stream().forEach(dataOut -> {
            Optional<WikiNamedResource> maybeDataIn = inList.stream()
                    .filter(spouseIn -> spouseIn.getLocalPart().equals(dataOut.getWikiPage()))
                    .findAny();
            assertTrue(maybeDataIn.isPresent());
            assertBaseNeoBaseData(dataOut, maybeDataIn.get());
        });
    }

    private void assertFullNeoPerson(NeoPerson personOut, WikiPerson personIn, Optional<GenderType> maybeGender) {
        assertBaseNeoBaseData(personOut, new WikiNamedResource(personIn.getWikiPage().getLocalPartNoUnderscore(), personIn.getName()));
        assertThat(personOut.getDateOfBirth().get(), is(personIn.getDateOfBirth()));
        assertThat(personOut.getDateOfDeath().get(), is(personIn.getDateOfDeath()));
        assertThat(personOut.getGender(), is(maybeGender));

        assertTrue(personOut.getFather().isPresent());
        assertBaseNeoPerson(personOut.getFather().get(), personIn.getFather(), Optional.of(GenderType.MALE));

        assertTrue(personOut.getMother().isPresent());
        assertBaseNeoPerson(personOut.getMother().get(), personIn.getMother(), Optional.of(GenderType.FEMALE));

        assertBaseNeoPersonList(personOut.getSpouses(), personIn.getSpouses());
        assertBaseNeoPersonList(personOut.getIssues(), personIn.getIssues());
        assertBaseNeoBaseDataList(personOut.getHouses(), personIn.getHouses());

        assertThat(personOut.getSuccessions().size(), is(personIn.getSuccessions().size()));
        personOut.getSuccessions().stream().forEach(jobOut -> {

            assertTrue(jobOut.getFrom().isPresent());

            Optional<WikiSuccession> maybeJobIn = personIn.getSuccessions().stream()
                    .filter(jobIn -> jobIn.getFrom().equals(jobOut.getFrom().get()))
                    .findAny();
            assertTrue(maybeJobIn.isPresent());
            WikiSuccession jobIn = maybeJobIn.get();

            assertThat(jobOut.getFrom().get(), is(jobIn.getFrom()));
            assertTrue(jobOut.getTo().isPresent());
            assertThat(jobOut.getTo().get(), is(jobIn.getTo()));
            assertThat(jobOut.getTitle(), is(jobIn.getTitleAndCountries().get().element1()));
            assertThat(toStringList(jobOut.getCountries(), c -> c.getName()), is(jobIn.getTitleAndCountries().get().element2()));

            assertBaseNeoPerson(jobOut.getPredecessor().get(), jobIn.getPredecessor(), Optional.empty());
            assertBaseNeoPerson(jobOut.getSuccessor().get(), jobIn.getSuccessor(), Optional.empty());
        });
    }

    private void assertBaseNeoPerson(NeoPerson personOut, WikiNamedResource personIn, Optional<GenderType> expectedGender) {
        assertBaseNeoBaseData(personOut, personIn);
        assertThat(personOut.getDateOfBirth(), is(Optional.empty()));
        assertThat(personOut.getDateOfDeath(), is(Optional.empty()));
        assertThat(personOut.getFather(), is(Optional.empty()));
        assertThat(personOut.getMother(), is(Optional.empty()));
        assertThat(personOut.getHouses(), hasSize(0));
        assertThat(personOut.getSpouses(), hasSize(0));
        assertThat(personOut.getIssues(), hasSize(0));
        assertThat(personOut.getGender(), is(expectedGender));
    }

    private void assertBaseNeoBaseData(NeoBaseData dataOut, WikiNamedResource dataIn) {
        assertThat(dataOut.getWikiPage(), is(dataIn.getLocalPartNoUnderscore()));
        assertThat(dataOut.getName(), anyOf( is(dataIn.getDisplayText()), is(dataIn.getLocalPartNoUnderscore()) ));
    }


    private WikiPerson person() {
        WikiPerson.Builder personBuilder = new WikiPerson.Builder()
                .name("William the Conqueror")
                .wikiPage(WikiResource.fromLocalPart("William the Conqueror"))
                .dateOfBirth(new FlexibleDate.Builder().year(1028).noMonth().noDay().build())
                .dateOfDeath(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                .father(new WikiNamedResource("Robert I, Duke of Normandy"))
                .mother(new WikiNamedResource("Herleva", "Herleva of Falaise"))
                .spouse(Arrays.asList(new WikiNamedResource("Matilda of Flanders")))
                .issue(Arrays.asList(
                        new WikiNamedResource("Robert Curthose"),
                        new WikiNamedResource("Richard of Normandy", "Richard"),
                        new WikiNamedResource("William II of England")
                ))
                .house(Arrays.asList(new WikiNamedResource("Norman dynasty")))
                .succession(new WikiSuccession.Builder()
                        .from(new FlexibleDate.Builder().year(1066).month(12).day(25).build())
                        .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                        .predecessor(new WikiNamedResource("Edgar the Ætheling"))
                        .successor(new WikiNamedResource("William II of England", "William II"))
                        .titleAndCountries(new Tuple2<>("King", Arrays.asList("England")))
                        .build())
                .succession(new WikiSuccession.Builder()
                        .from(new FlexibleDate.Builder().year(1035).month(7).day(3).build())
                        .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                        .predecessor(new WikiNamedResource("Robert the Magnificent"))
                        .successor(new WikiNamedResource("Robert Curthose"))
                        .titleAndCountries(new Tuple2<>("Duke", Arrays.asList("Normandy")))
                        .build());

        return personBuilder.build();
    }

    private WikiPerson personBase() {
        WikiPerson.Builder personBuilder = new WikiPerson.Builder()
                .name("William the Conqueror")
                .wikiPage(WikiResource.fromLocalPart("William the Conqueror"));
        return personBuilder.build();
    }

    private WikiPerson child() {
        WikiPerson.Builder personBuilder = new WikiPerson.Builder()
                .name("William II of England")
                .wikiPage(WikiResource.fromLocalPart("William II of England"))
                .dateOfBirth(new FlexibleDate.Builder().year(1056).noMonth().noDay().build())
                .dateOfDeath(new FlexibleDate.Builder().year(1100).month(8).day(2).build())
                .father(new WikiNamedResource("William the Conqueror"))
                .mother(new WikiNamedResource("Matilda of Flanders"))
                .spouse(Arrays.asList())
                .issue(Arrays.asList())
                .house(Arrays.asList(new WikiNamedResource("Norman dynasty")));
        return personBuilder.build();
    }
}
