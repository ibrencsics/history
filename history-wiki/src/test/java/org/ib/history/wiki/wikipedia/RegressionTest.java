package org.ib.history.wiki.wikipedia;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.apache.commons.lang3.StringEscapeUtils;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiSuccession;
import org.ib.history.wiki.service.WikiService;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RegressionTest {

//    @Test
    public void download() throws IOException {
        String page = "William_the_Conqueror";
        String dir = "/home/ivan/Projects/Experiments/github/history/history-wiki/src/test/resources/testpages/";

        MediaWikiBot mediaWikiBot = new MediaWikiBot( "http://en.wikipedia.org/w/" );
        Article article = mediaWikiBot.getArticle(page);
        String wikiText = article.getSimpleArticle().getText();

        File file = new File(dir + page);
        file.createNewFile();

        PrintWriter writer = new PrintWriter(file);
        writer.print(wikiText);

        writer.close();
    }

    @Test
    public void testParser() {
        WikiService service = new WikiServiceWikipedia();
        WikiPerson person = service.getPerson("William_the_Conqueror");
        System.out.println(person);

        assertEquals("William the Conqueror", person.getName());
        assertEquals(new FlexibleDate.Builder().year(1028).noMonth().noDay().build(), person.getDateOfBirth());
        assertEquals(new FlexibleDate.Builder().year(1087).month(9).day(9).build(), person.getDateOfDeath());

        assertEquals(new WikiNamedResource("Robert I, Duke of Normandy"), person.getFather());
        assertEquals(new WikiNamedResource("Herleva", "Herleva of Falaise"), person.getMother());

        assertEquals(Arrays.asList(new WikiNamedResource("Matilda of Flanders")), person.getSpouses());
        assertEquals(Arrays.asList(
                new WikiNamedResource("Robert Curthose"),
                new WikiNamedResource("Richard of Normandy", "Richard"),
                new WikiNamedResource("William II of England"),
                // Matilda
                new WikiNamedResource("Cecilia of Normandy", "Cecilia"),
                new WikiNamedResource("Henry I of England"),
                new WikiNamedResource("Adeliza of Normandy", "Adeliza"),
                new WikiNamedResource("Constance of Normandy", "Constance"),
                new WikiNamedResource("Adela of Normandy", "Adela, Countess of Blois")
                // Agatha of Normandy (existence doubtful)
        ), person.getIssues());
        assertEquals(Arrays.asList(new WikiNamedResource("Norman dynasty")), person.getHouses());

        assertEquals(2, person.getSuccessions().size());

        WikiSuccession expSuccession1 = new WikiSuccession.Builder()
                .from(new FlexibleDate.Builder().year(1066).month(12).day(25).build())
                .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                .predecessor(new WikiNamedResource("Edgar the Ætheling"))
                .successor(new WikiNamedResource("William II of England", "William II"))
                // predecessor [[Harold Godwinson|Harold II]]
                .build();

        WikiSuccession expSuccession2 = new WikiSuccession.Builder()
                .from(new FlexibleDate.Builder().year(1035).month(7).day(3).build())
                .to(new FlexibleDate.Builder().year(1087).month(9).day(9).build())
                .predecessor(new WikiNamedResource("Robert the Magnificent"))
                .successor(new WikiNamedResource("Robert Curthose"))
                .build();

        assertEquals(Arrays.asList(expSuccession1, expSuccession2), person.getSuccessions());
    }

    @Test
    public void test() {
    }
}
