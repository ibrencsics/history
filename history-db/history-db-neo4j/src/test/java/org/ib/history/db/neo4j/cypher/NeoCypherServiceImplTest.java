package org.ib.history.db.neo4j.cypher;

import org.ib.history.db.neo4j.NeoBaseTest;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

public class NeoCypherServiceImplTest extends NeoBaseTest {

    protected NeoCypherService neoCypherService;

    @Before
    public void prepareService() {
        neoCypherService = new NeoCypherServiceImpl(graphDb);
    }

    @Test
    public void testGetPersonsByPattern() {
        populateDb();

        List<String> suggestions = neoCypherService.getPersonsByPattern("abc");
//        assertThat(suggestions.size(), is(1));
    }

    private void populateDb() {
        neoService.save(new WikiPerson.Builder().wikiPage(WikiResource.fromLocalPart("Abc_def")).name("abcde").build());
        neoService.save(new WikiPerson.Builder().wikiPage(WikiResource.fromLocalPart("Abcd_ef")).name("sAbcde").build());
    }
}
