package org.ib.history.wiki.parser;

import org.junit.Test;

import java.util.Map;
import java.util.Optional;

public class RoyaltyParserTest extends BaseParserTest {

    private RoyaltyParser parser = new RoyaltyParser();

    @Test
    public void test1() throws Exception {
        Royalty royalty = parser.parse(testPage("William_III_of_England"));
//        Royalty royalty = parser.parse(testPage("Charles_V,_Holy_Roman_Emperor"));
//        Royalty royalty = parser.parse(testPage("George_II_of_Great_Britain"));
//        Royalty royalty = parser.parse(testPage("Winston_Churchill"));
        System.out.println(royalty);
    }

}
