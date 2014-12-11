package org.ib.history.wiki.parser;

import junit.framework.Assert;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static junit.framework.Assert.*;

public class TemplateParserTest extends BaseParserTest {


//    @Test
    public void testGetTemplateOffline() throws IOException {
        TemplateParser parser = new TemplateParser();

        Optional<String> template = parser.getTemplate(offlineTestPage("1.txt"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(offlineTestPage("2.txt"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(offlineTestPage("3.txt"), "Infobox royalty");
        System.out.println(template.get());
    }

//    @Test
    public void testGetTemplateOnline() throws IOException {
        TemplateParser parser = new TemplateParser();

        Optional<String> template = parser.getTemplate(testPage("William_III_of_England"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(testPage("Charles_V,_Holy_Roman_Emperor"), "Infobox royalty");
        System.out.println(template.get());

        template = parser.getTemplate(testPage("George_II_of_Great_Britain"), "Infobox royalty");
        System.out.println(template.get());
    }
}
