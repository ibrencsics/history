package org.ib.history.wiki.wikipedia;

import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.wiki.domain.*;
import org.ib.history.wiki.parser.RoyaltyParser;
import org.ib.history.wiki.parser.TemplateParser;
import org.ib.history.wiki.service.WikiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WikiServiceWikipedia implements WikiService {

    private TemplateParser templateParser = new TemplateParser();

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

        parseSuccession(builder, royalty);

        return builder.build();
    }

    private void parseSuccession(WikiPerson.Builder builder, Royalty royalty) {
        for (Royalty.Succession succession : royalty.getSuccessions()) {
            String sentence = succession.getSuccessionNoLinksNoSmall();

            if (sentence != null) {
                WikiSuccession.Builder successionBuilder = new WikiSuccession.Builder()
                        .from(succession.getFrom())
                        .to(succession.getTo())
                        .predecessor(succession.getPredecessor())
                        .successor(succession.getSuccessor());

                List<String> words = templateParser.sentenceToWords(sentence);

                if (Collections.frequency(words, "of") > 1) {
                    successionBuilder.raw(sentence);
                } else {
                    StringBuilder titleBuilder = new StringBuilder();
                    List<String> countries = new ArrayList<>();
                    boolean isTitle = true;

                    for (String word : words) {
                        if (word.equals("of")) {
                            isTitle = false;
                            continue;
                        }

                        if (Arrays.asList("and", "the", "disputed").contains(word)) {
                            continue;
                        }

                        if (isTitle) {
                            titleBuilder.append(word + " ");
                        } else {
                            countries.add(word);
                        }


                    }

                    successionBuilder.titleAndCountries(
                            new Tuple2<>(
                                    titleBuilder.toString().trim(),
                                    countries
                            )
                    );
                }

                builder.succession(successionBuilder.build());
            }
        }
    }
}