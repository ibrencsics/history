package org.ib.history.wiki.parser;

import org.ib.history.wiki.domain.Royalty;
import org.junit.Test;


public class RoyaltyParserTest extends BaseParserTest {

    private RoyaltyParser parser = new RoyaltyParser();

    @Test
    public void test1() throws Exception {
        String pageName;
//        pageName = "William_III_of_England";
//        pageName = "Charles_V,_Holy_Roman_Emperor";
//        pageName = "George_II_of_Great_Britain";
        pageName = "Matthias_Corvinus";

        Royalty royalty = parser.parse(pageName, testPage(pageName));
        System.out.println(royalty);
    }

}
