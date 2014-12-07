package org.ib.history.wiki.parser;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.junit.Test;

import java.io.IOException;

public class WikiParserTest {

//    @Test
    public void testGetRoyaltyInfoboxText() {
        WikiParser wikiParser = new WikiParser();
        System.out.println(wikiParser.getRoyaltyInfoboxText(testPage()));
    }

    @Test
    public void test() throws IOException {
        WikiParser wikiParser = new WikiParser();
        RoyaltyInfobox info = wikiParser.getRoyaltyInfobox(testPage());
        System.out.println(info.getName());
        info.getSuccessions().forEach( s -> {
            System.out.println(s.getTitle());
            System.out.println(s.getCountries());
            System.out.println(s.getFrom());
        });
    }

    private String testPage() {
        MediaWikiBot mediaWikiBot = new MediaWikiBot( "http://en.wikipedia.org/w/" );
        Article article = mediaWikiBot.getArticle("William_III_of_England");
//        Article article = mediaWikiBot.getArticle("George_I_of_Great_Britain");
        return article.getSimpleArticle().getText();
    }
}
