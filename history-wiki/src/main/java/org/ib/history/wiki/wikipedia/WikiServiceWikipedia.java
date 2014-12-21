package org.ib.history.wiki.wikipedia;

import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.domain.WikiResource;
import org.ib.history.wiki.domain.Royalty;
import org.ib.history.wiki.parser.RoyaltyParser;
import org.ib.history.wiki.service.WikiService;

public class WikiServiceWikipedia implements WikiService {

    @Override
    public WikiPerson getPerson(String wikiPage) {
        RoyaltyParser parser = new RoyaltyParser();
        Royalty royalty = parser.parse(wikiPage);
        System.out.println(royalty);

        WikiPerson.Builder builder = new WikiPerson.Builder();

        builder.wikiPage(WikiResource.fromLocalPart(wikiPage))
                .name(royalty.getName())
                .dateOfBirth(royalty.getDateOfBirth())
                .dateOfDeath(royalty.getDateOfDeath())
                .father(royalty.getFather())
                .mother(royalty.getMother())
                .spouse(royalty.getSpouses())
                .issue(royalty.getIssues())
                .house(royalty.getHouses());


        return builder.build();
    }
}
