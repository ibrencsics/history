package org.ib.history.db.neo4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CoreNeoServiceNeoPersonTest {

    private static Logger logger = LogManager.getLogger(CoreNeoServiceTest.class);

    private GraphDatabaseService graphDb;
    private NeoService neoService;

    @Before
    public void prepareTestDatabase() {
        graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
        neoService = new CoreNeoService(graphDb);
    }

    @After
    public void destroyTestDatabase() {
        graphDb.shutdown();
    }

    @Test
    public void test() {
        WikiPerson personIn = person();
        neoService.save(personIn);

        Optional<NeoPerson> maybePersonOut = neoService.getNeoPerson(personIn.getWikiPage().getLocalPart());

        if (maybePersonOut.isPresent()) {
            NeoPerson personOut = maybePersonOut.get();

            assertThat(personOut.getWikiPage(), is(personIn.getWikiPage().getLocalPartNoUnderscore()));
            assertThat(personOut.getName(), is(personIn.getName()));
            assertThat(personOut.getDateOfBirth().get(), is(personIn.getDateOfBirth()));
            assertThat(personOut.getDateOfDeath().get(), is(personIn.getDateOfDeath()));
        } else {
            fail("No WikiPerson found");
        }

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
                .house(Arrays.asList(new WikiNamedResource("Norman dynasty")));
        return personBuilder.build();
    }
}
