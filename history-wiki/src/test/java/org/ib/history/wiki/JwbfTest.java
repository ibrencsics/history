package org.ib.history.wiki;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.junit.Test;

public class JwbfTest {

    @Test
    public void test() {
        MediaWikiBot mediaWikiBot = new MediaWikiBot( "http://en.wikipedia.org/w/" );
//        mediaWikiBot.login( "CurrentlyDeployedBot", "password" );
        Article article = mediaWikiBot.getArticle("George_I_of_Great_Britain");
        SimpleArticle simpleArticle = article.getSimpleArticle();
        System.out.println(article.getSimpleArticle().getText());
//        System.out.println(simpleArticle);
    }
}
