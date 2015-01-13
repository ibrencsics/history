package org.ib.history.wiki.wikipedia;

import org.ib.history.wiki.domain.Royalty;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.parser.RoyaltyParser;
import org.ib.history.wiki.parser.TemplateParser;
import org.ib.history.wiki.service.WikiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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

        assertEquals(expected.successions, person.getSuccessions());
    }

//    @Test
    public void testRemoveLinks() {
        TemplateParser templateParser = new TemplateParser();

        RoyaltyParser parser = new RoyaltyParser();
        Royalty royalty = parser.parse(page);

        royalty.getSuccessions().stream().forEach(s -> {
//            System.out.println(s.getSuccessionRaw());
//            System.out.println("\t" + s.getSuccessionNoLinks());
            String sentence = s.getSuccessionNoLinksNoSmall();
            if (sentence!=null) {
                System.out.println("\t" + sentence);
                System.out.println("\t\t" + templateParser.sentenceToWords(sentence));
            }
        });
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
                { "James_II_of_England" },
                { "Henry_V_of_England" },
                { "Edward_VII" },
                { "Matthias_Corvinus" },

//                { "Charles_II_of_England" }, // problem with reign
        });
    }
}
