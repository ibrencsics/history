package org.ib.history.wiki.parser;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.FlexibleDateComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            List<String> tokens = templateParser.getTemplateData(template.get());
            List<String> tokensFiltered = tokens.stream().filter(this::isNumeric).collect(Collectors.toList());
            builder.fromList(tokensFiltered);
            return builder.build();
        }

        template = templateParser.getTemplate(data, TEMPLATE_BIRTH_DATE_AND_AGE, TEMPLATE_DEATH_DATE_AND_AGE);
        if (template.isPresent()) {
            List<String> tokens = templateParser.getTemplateData(template.get());
            List<String> tokensFiltered = tokens.stream().filter(this::isNumeric).collect(Collectors.toList()).subList(0,3);
            builder.fromList(tokensFiltered);
            return builder.build();
        }

//        Pattern pattern = Pattern.compile("(?i)([1-9]|[1-2]\\d|3[0-1])\\s(January|February|March|April|May|June|July|August|September|October|November|December)\\s(\\d{3,4})");
//        Matcher matcher = pattern.matcher(data);
//        if (matcher.find()) {
//            builder.day(matcher.group(1));
//            builder.monthByName(matcher.group(2));
//            builder.year(matcher.group(3));
//            return builder.build();
//        }

        List<FlexibleDate> flexDates = parseDateEnglishFormat(data);
        if (flexDates.size() == 1) {
            return flexDates.get(0);
        }

        return builder.build();
    }

    public List<FlexibleDate> parseDateEnglishFormat(String data) {
        String dayPattern = "[1-9]|[1-2]\\d|3[0-1]";
        String monthPattern = "January|February|March|April|May|June|July|August|September|October|November|December";
//        String yearPattern = "\\d{3,4}";
        String yearPattern = "\\d{4}";

        List<FlexibleDate> flexDates = new ArrayList<>(2);

        String patternStr;
        Pattern pattern;
        Matcher matcher;

        // 15 January 2014
        patternStr = "(?i)(" + dayPattern + ")\\s(" + monthPattern + ")\\s(" + yearPattern + ")";
        pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(data);

        while (matcher.find()) {
            Integer day = Integer.parseInt(matcher.group(1));
            String month = matcher.group(2);
            Integer year = Integer.parseInt(matcher.group(3));

            FlexibleDate flexDate = new FlexibleDate.Builder()
                    .day(day).monthByName(month).year(year).build();
            flexDates.add(flexDate);
        }
        data = data.replaceAll(patternStr, "");

        // January 2014
        patternStr = "(?i)(" + monthPattern + ")\\s(" + yearPattern + ")";
        pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(data);

        while (matcher.find()) {
            String month = matcher.group(1);
            Integer year = Integer.parseInt(matcher.group(2));

            FlexibleDate flexDate = new FlexibleDate.Builder()
                    .noDay().monthByName(month).year(year).build();
            flexDates.add(flexDate);
        }
        data = data.replaceAll(patternStr, "");

        // 2014
        patternStr = yearPattern;
        pattern = Pattern.compile(patternStr);
        matcher = pattern.matcher(data);

        while (matcher.find()) {
            Integer year = Integer.parseInt(matcher.group());
            FlexibleDate flexDate = new FlexibleDate.Builder()
                    .noDay().noMonth().year(year).build();
            flexDates.add(flexDate);
        }

        return flexDates;
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
