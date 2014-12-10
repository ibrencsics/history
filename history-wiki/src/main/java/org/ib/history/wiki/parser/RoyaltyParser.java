package org.ib.history.wiki.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class RoyaltyParser {

    private Map<String,BiConsumer<Royalty,String>> parserMap = new HashMap<>(10);

    public RoyaltyParser() {
        parserMap.put("name", ((object, value) -> { object.setName(value); }));
        parserMap.put("birth_date", this::parseDate);
        parserMap.put("death_date", this::parseDate);
    }

    public Royalty parse(Map<String,String> data) {
        Royalty royalty = new Royalty();

        for (Map.Entry<String,String> entry : data.entrySet()) {
            BiConsumer<Royalty, String> parser = parserMap.get(entry.getKey());
            if (parser != null) {
                parser.accept(royalty, entry.getValue());
            }
        }

        return royalty;
    }

    private void parseDate(Royalty royalty, String data) {
        TemplateParser templateParser = new TemplateParser();
        Optional<String> template = templateParser.getTemplate(data,
                "birth date", "birth date and age", "death date", "death date and age");
        if (template.isPresent()) {
            System.out.println("date: " + template.get());
        }
    }

    private void parseDateTemplate(Royalty royalty, String data) {


    }

    // birth_date : {{birth date|df=yes|1650|11|4}}<br />{{small|[<nowiki />[[Old Style and New Style dates|N.S.]]: 14 November 1650<nowiki />]}}<ref name=OSNS>During William's lifetime, two calendars were in use in Europe: the Old Style [[Julian calendar]] in Britain and parts of Northern and Eastern Europe, and the New Style [[Gregorian calendar]] elsewhere, including William's birthplace in the Netherlands. At the time of William's birth, Gregorian dates were ten days ahead of Julian dates: thus William was born on 14 November 1650 by Gregorian reckoning, but on 4 November 1650 by Julian. At William's death, Gregorian dates were eleven days ahead of Julian dates. He died on 8 March 1702 by the standard Julian calendar, but on 19 March 1702 by the Gregorian calendar. (However, the English New Year fell on 25 March, so by English reckoning of the time, William died on 8 March 1701.) Unless otherwise noted, dates in this article follow the standard Julian calendar, in which the New Year falls on 1 January.</ref>
    // death_date : {{death date and age|df=yes|1702|3|8|1650|11|4}}<br /><small>[<nowiki />[[Old Style and New Style dates|N.S.]]: 19 March 1702<nowiki />]</small>
    // birth_date : 24 February 1500
    // death_date : 21 September 1558 (aged 58)
    // birth_date : 30 October / 9 November 1683{{ref|dates|O.S./N.S.}}
    // death_date : {{Death date and age|1760|10|25|1683|11|9|df=yes}}
}
