package org.ib.history.wiki.parser;

import java.io.IOException;

public class WikiParser {

    private RoyaltyInfoboxParser royaltyInfoboxParser = new RoyaltyInfoboxParser();

    public RoyaltyInfobox getRoyaltyInfobox(String wikiText) throws IOException {
        return royaltyInfoboxParser.parse(wikiText);
    }

}
