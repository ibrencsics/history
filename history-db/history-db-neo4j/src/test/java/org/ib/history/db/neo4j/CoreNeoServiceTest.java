package org.ib.history.db.neo4j;

import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class CoreNeoServiceTest {

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
    public void testSave() {
        long id = neoService.save(person());
        assertThat(id, is(0L));
        id = neoService.save(person());
        assertThat(id, is(1L));
    }

    private WikiPerson person() {
        WikiPerson.Builder personBuilder = new WikiPerson.Builder();
        personBuilder.name("Béla");
        personBuilder.wikiPage(WikiResource.fromLocalPart("BélaPage"));
        return personBuilder.build();
    }
}
