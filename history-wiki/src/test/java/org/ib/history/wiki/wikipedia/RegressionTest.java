package org.ib.history.wiki.wikipedia;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.service.WikiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class RegressionTest {

    private String page;
    private RegressionTestData.TestVector expected;

    public RegressionTest(String page) {
        this.page = page;
        this.expected = RegressionTestData.expectedData.get(page);
    }

    @Test
    public void testParser() {
        WikiService service = new WikiServiceWikipedia();
        WikiPerson person = service.getPerson(page);
        System.out.println(person);

        assertEquals(expected.name, person.getName());
        assertEquals(expected.dateOfBirth, person.getDateOfBirth());
        assertEquals(expected.dateOfDeath, person.getDateOfDeath());

        assertEquals(expected.father, person.getFather());
        assertEquals(expected.mother, person.getMother());

        assertEquals(expected.spouses, person.getSpouses());
        assertEquals(expected.issues, person.getIssues());
        assertEquals(expected.houses, person.getHouses());

//        assertEquals(expected.successions(), person.getSuccessions());
    }

//    @Test
    public void test() {
    }

    @Parameterized.Parameters()
    public static Iterable<Object[]> data1() {
        return Arrays.asList(new Object[][] {
                { "William_the_Conqueror" },
                { "William_III_of_England" },
                { "Henry_VIII_of_England" },
                { "Louis_I_of_Hungary" },
                { "Anne_of_Austria" },
                { "Empress_Matilda" },
                { "Stephen,_King_of_England" },
                { "Charles_II_of_England" },
        });
    }
}
