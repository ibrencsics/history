package org.ib.history.wiki.wikipedia;

import org.ib.history.wiki.domain.*;
import org.ib.history.wiki.parser.RoyaltyParser;
import org.ib.history.wiki.service.WikiService;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WikiServiceWikipedia implements WikiService {

    @Override
    public WikiPerson getPerson(String wikiPage) {
        RoyaltyParser parser = new RoyaltyParser();
        Royalty royalty = parser.parse(wikiPage);
//        System.out.println(royalty);

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

        parseSuccession2(builder, royalty);

        return builder.build();
    }

    // Succession{, countries=[{p='King of Hungary'}, {p='King of Croatia', t='Croatia'}], from=1342-7-21, to=1382-9-10}
    // Succession{, countries=[{p='King of Poland'}], from=1370-11-17, to=1382-9-10}}
    // Succession{, countries=[{p='Principality of Orange', t='Prince of Orange'}], from=1650-11-4, to=1702-3-8}
    // Succession{, countries=[{p='Stadtholder', t='Stadtholder of Holland, Zeeland, Utrecht, Gelderland and Overijssel'}], from=1672-7, to=1702-3-8}
    // Succession{, countries=[{p='List of English monarchs', t='King of England'}, {p='List of Scottish monarchs', t='Scotland'}, {p='King of Ireland', t='Ireland'}], from=1689-2-13, to=1702-3-8}}
    private void parseSuccession(WikiPerson.Builder builder, Royalty royalty) {
        for (Royalty.Succession succession : royalty.getSuccessions()) {
            String concatenated = "";
            if (succession.getCountries().size()==1) {
                concatenated = succession.getCountries().get(0).getDisplayText();
            }
            else if (succession.getCountries().size()>1) {
                concatenated = succession.getCountries().stream().map(s -> s.getDisplayText()).collect(Collectors.joining(", "));
            }

            String[] tokens = concatenated.replace(",", "").split("\\s");

            boolean isTitle = true;
            String title = "";
            for (int i=0; i<tokens.length; i++) {
                if (tokens[i].equalsIgnoreCase("of")) {
                    isTitle = false;
                    continue;
                }

                if (isTitle) {
                    title += tokens[i] + " ";
                    continue;
                }

                if (!Arrays.asList("and").contains(tokens[i])) {
                    String country = tokens[i];

                    WikiSuccession.Builder successionBuilder = new WikiSuccession.Builder()
                            .country(country.trim())
                            .title(title.trim())
                            .from(succession.getFrom())
                            .to(succession.getTo())
                            .predecessor(succession.getPredecessor())
                            .successor(succession.getSuccessor());
                    builder.succession(successionBuilder.build());
                }
            }
        }
    }

    private void parseSuccession2(WikiPerson.Builder builder, Royalty royalty) {
        for (Royalty.Succession succession : royalty.getSuccessions()) {
            if (!succession.getCountries().isEmpty()) {
                for (WikiNamedResource country : succession.getCountries()) {
                    WikiSuccession.Builder successionBuilder = new WikiSuccession.Builder()
                            .title(country.getDisplayText().trim())
                            .from(succession.getFrom())
                            .to(succession.getTo())
                            .predecessor(succession.getPredecessor())
                            .successor(succession.getSuccessor());
                    builder.succession(successionBuilder.build());
                }
            }
            else {
                WikiSuccession.Builder successionBuilder = new WikiSuccession.Builder()
                        .title(succession.getCountriesRaw())
                        .from(succession.getFrom())
                        .to(succession.getTo())
                        .predecessor(succession.getPredecessor())
                        .successor(succession.getSuccessor());
                builder.succession(successionBuilder.build());
            }
        }
    }
}