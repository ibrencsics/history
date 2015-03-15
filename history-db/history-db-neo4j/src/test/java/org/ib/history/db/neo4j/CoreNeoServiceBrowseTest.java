package org.ib.history.db.neo4j;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@Ignore
public class CoreNeoServiceBrowseTest extends NeoBaseTest {

    @Test
    public void test() {
        neoService.save(father());
        assertWikiPerson(father());

        neoService.save(son1());
        assertWikiPerson(son1());

        neoService.save(father());
        assertWikiPerson(father());
    }

    private void assertWikiPerson(WikiPerson personIn) {
//        Optional<WikiPerson> maybePersonOut = neoService.getPerson(personIn.getWikiPage().getLocalPartNoUnderscore());
//
//        if (maybePersonOut.isPresent()) {
//            WikiPerson personOut = maybePersonOut.get();
//
//            assertThat(personOut.getWikiPage(), is(personIn.getWikiPage()));
//            assertThat(personOut.getName(), is(personIn.getName()));
//            assertThat(personOut.getDateOfBirth(), is(personIn.getDateOfBirth()));
//            assertThat(personOut.getDateOfDeath(), is(personIn.getDateOfDeath()));
//
//            assertThat(personOut.getSpouses(), is(personIn.getSpouses()));
//            assertThat(personOut.getIssues(), is(personIn.getIssues()));
//            assertThat(personOut.getHouses(), is(personIn.getHouses()));
//        } else {
//            fail("No WikiPerson found");
//        }
    }

    private WikiPerson father() {
        WikiPerson.Builder personBuilder = new WikiPerson.Builder()
                .name("The Father")
                .wikiPage(WikiResource.fromLocalPart("The Father"))
                .dateOfBirth(new FlexibleDate.Builder().year(1028).noMonth().noDay().build())
                .dateOfDeath(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                .father(new WikiNamedResource("The Grandfather"))
                .mother(new WikiNamedResource("The Grandmother"))
                .spouse(Arrays.asList(new WikiNamedResource("The Mother")))
                .issue(Arrays.asList(
                        new WikiNamedResource("Son1 of Father"),
                        new WikiNamedResource("Son2 of Father")
                ))
                .house(Arrays.asList(new WikiNamedResource("Norman dynasty")));
        return personBuilder.build();
    }

    private WikiPerson son1() {
        WikiPerson.Builder personBuilder = new WikiPerson.Builder()
                .name("Son1 of Father")
                .wikiPage(WikiResource.fromLocalPart("Son1 of Father"))
                .dateOfBirth(new FlexibleDate.Builder().year(1128).noMonth().noDay().build())
                .dateOfDeath(new FlexibleDate.Builder().year(1187).month(9).day(9).build())
                .father(new WikiNamedResource("The Father"))
                .mother(new WikiNamedResource("The Mother"))
                .spouse(Arrays.asList(new WikiNamedResource("The Spouse of the Son 1")))
                .issue(Arrays.asList(
                        new WikiNamedResource("Son1 of Son1"),
                        new WikiNamedResource("Son2 of Son1")
                ))
                .house(Arrays.asList(new WikiNamedResource("Norman dynasty")));
        return personBuilder.build();
    }
}
