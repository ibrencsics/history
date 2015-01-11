package org.ib.history.wiki.wikipedia;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class DownloadPage {

    public static void main(String[] args) throws IOException {
        String page = "Matthias_Corvinus";
        String dir = "/home/ivan/Projects/Experiments/github/history/history-wiki/src/test/resources/testpages/";

        MediaWikiBot mediaWikiBot = new MediaWikiBot( "http://en.wikipedia.org/w/" );
        Article article = mediaWikiBot.getArticle(page);
        String wikiText = article.getSimpleArticle().getText();

        File file = new File(dir + page + ".txt");
        file.createNewFile();

        PrintWriter writer = new PrintWriter(file);
        writer.print(wikiText);

        writer.close();
    }
}
