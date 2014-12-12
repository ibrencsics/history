package org.ib.history.wiki.parser;

import org.ib.history.commons.data.FlexibleDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// birth_date : {{birth date|df=yes|1650|11|4}}<br />{{small|[<nowiki />[[Old Style and New Style dates|N.S.]]: 14 November 1650<nowiki />]}}<ref name=OSNS>During William's lifetime, two calendars were in use in Europe: the Old Style [[Julian calendar]] in Britain and parts of Northern and Eastern Europe, and the New Style [[Gregorian calendar]] elsewhere, including William's birthplace in the Netherlands. At the time of William's birth, Gregorian dates were ten days ahead of Julian dates: thus William was born on 14 November 1650 by Gregorian reckoning, but on 4 November 1650 by Julian. At William's death, Gregorian dates were eleven days ahead of Julian dates. He died on 8 March 1702 by the standard Julian calendar, but on 19 March 1702 by the Gregorian calendar. (However, the English New Year fell on 25 March, so by English reckoning of the time, William died on 8 March 1701.) Unless otherwise noted, dates in this article follow the standard Julian calendar, in which the New Year falls on 1 January.</ref>
// death_date : {{death date and age|df=yes|1702|3|8|1650|11|4}}<br /><small>[<nowiki />[[Old Style and New Style dates|N.S.]]: 19 March 1702<nowiki />]</small>
// birth_date : 24 February 1500
// death_date : 21 September 1558 (aged 58)
// birth_date : 30 October / 9 November 1683{{ref|dates|O.S./N.S.}}
// death_date : {{Death date and age|1760|10|25|1683|11|9|df=yes}}

public class DateParser {

    private static final String TEMPLATE_BIRTH_DATE = "birth date";
    private static final String TEMPLATE_DEATH_DATE = "death date";
    private static final String TEMPLATE_BIRTH_DATE_AND_AGE = "birth date and age";
    private static final String TEMPLATE_DEATH_DATE_AND_AGE = "death date and age";

    private TemplateParser templateParser = new TemplateParser();

    public FlexibleDate parse(String data) {
        FlexibleDate.Builder builder = new FlexibleDate.Builder();

        Optional<String> template = templateParser.getTemplate(data, TEMPLATE_BIRTH_DATE, TEMPLATE_DEATH_DATE);
        if (template.isPresent()) {
            System.out.println("date: " + template.get());
            List<String> tokens = templateParser.getTemplateData(template.get());
            List<String> tokensFiltered = tokens.stream().filter(this::isNumeric).collect(Collectors.toList());
            System.out.println(tokensFiltered);
            builder.fromList(tokensFiltered);
        }

        template = templateParser.getTemplate(data, TEMPLATE_BIRTH_DATE_AND_AGE, TEMPLATE_DEATH_DATE_AND_AGE);
        if (template.isPresent()) {
            System.out.println("date: " + template.get());
            List<String> tokens = templateParser.getTemplateData(template.get());
            List<String> tokensFiltered = tokens.stream().filter(this::isNumeric).collect(Collectors.toList()).subList(0,3);
            System.out.println(tokensFiltered);
            builder.fromList(tokensFiltered);
        }

        System.out.println(data);

        return builder.build();
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    // ? zero or one
    // * zero or more
    // + one or more

    // \d == [0-9] digit
    // \D == [^0-9] non-digit

    // http://www.mkyong.com/regular-expressions/how-to-validate-date-with-regular-expression/
}
