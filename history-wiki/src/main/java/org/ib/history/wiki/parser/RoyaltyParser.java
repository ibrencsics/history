package org.ib.history.wiki.parser;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.FlexibleDateComparator;
import org.ib.history.commons.data.PageLink;
import org.ib.history.commons.tuples.Tuple2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoyaltyParser {

    private TemplateParser templateParser = new TemplateParser();
    private DateParser dateParser = new DateParser();

    private Map<String,BiConsumer<Royalty,Tuple2<String,Integer>>> parserMap = new HashMap<>(10);

    public RoyaltyParser() {
        parserMap.put("name", ((royalty, data) -> { royalty.setName(data.element1()); }));
        parserMap.put("birth_date", ((royalty, data) -> { royalty.setDateOfBirth(dateParser.parse(data.element1())); }));
        parserMap.put("death_date", this::parseDeathDate);
        parserMap.put("father", this::parseFather);
        parserMap.put("mother", this::parseMother);
        parserMap.put("succession", this::parseSuccession);
        parserMap.put("reign", this::parseReign);
    }

    public Royalty parse(String wikiText) {
        Optional<String> template = templateParser.getTemplate(wikiText, "Infobox royalty");
        Map<String,String> data = templateParser.getTemplateDataMap(template.get());
        return parse(data);
    }

    public Royalty parse(Map<String,String> data) {
        Royalty royalty = new Royalty();

        for (Map.Entry<String,String> entry : data.entrySet()) {

            // removing trailing numbers
            Integer seqNum = 0;

            Pattern pattern = Pattern.compile("\\d+$");
            Matcher matcher = pattern.matcher(entry.getKey());
            if (matcher.find()) {
                seqNum = Integer.parseInt(matcher.group());
            }

            final String parserKey = entry.getKey().replaceAll("\\d*$", "");

            BiConsumer<Royalty, Tuple2<String,Integer>> parser = parserMap.get(parserKey);
            if (parser != null) {
                System.out.println("to parse " + entry.getKey() + ": " + entry.getValue());
                parser.accept(royalty, new Tuple2<>(entry.getValue(), seqNum));
            }
        }

        return royalty;
    }

    private void parseBirthDate(Royalty royalty, Tuple2<String,Integer> data) {
        royalty.setDateOfBirth(dateParser.parse(data.element1()));
    }

    private void parseDeathDate(Royalty royalty, Tuple2<String,Integer> data) {
        royalty.setDateOfDeath(dateParser.parse(data.element1()));
    }

    private void parseFather(Royalty royalty, Tuple2<String,Integer> data) { royalty.setFather(templateParser.parseLink(data.element1())); }

    private void parseMother(Royalty royalty, Tuple2<String,Integer> data) { royalty.setMother(templateParser.parseLink(data.element1())); }

    // [[King of Great Britain]] [[King of Ireland|and Ireland]]<br />[[Electorate of Brunswick-Lüneburg|Elector of Hanover]]
    // [[Stadtholder|Stadtholder of Holland, Zeeland, Utrecht, Gelderland and Overijssel]]
    // [[List of English monarchs|King of England]], [[List of Scottish monarchs|Scotland]] and [[King of Ireland|Ireland]]
    // [[Principality of Orange|Prince of Orange]]
    // [[King of Spain]]<br>{{small|(alongside [[Joanna of Castile|Joanna]] until 1555)}}
    // [[Seventeen Provinces|Lord of the Netherlands]]; <br>[[List of counts of Burgundy|Count Palatine of Burgundy]]
    // [[Holy Roman Emperor]];<br>[[List of German monarchs|King of Germany]];<br>[[King of Italy]]
    // [[King of Bohemia]]<br/><small>contested till 1471 by [[George of Poděbrady]], from 1471 by [[Vladislas II of Bohemia and Hungary|Vladislaus II]]</small>
    // [[Duke of Austria]]<br/><small>contested by [[Frederick III, Holy Roman Emperor|Frederick V]]</small>
    // [[King of Hungary]] and [[King of Croatia|Croatia]]
    private void parseSuccession(Royalty royalty, Tuple2<String,Integer> data) {
        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        String dataText = data.element1();

        // TODO: better preprocessing
        if (dataText.contains("small")) {
            dataText = dataText.substring(0, dataText.indexOf("small"));
        }

        List<PageLink> links = templateParser.getLinks(dataText);
        succession.setCountries(links);
    }

    // 28 June 1519 – 27 August 1556<ref>Date of Charles's abdication; on 24 February 1558, the college of electors assembled at Frankfort accepted the instrument of Charles V's imperial resignation and declared the election of Ferdinand as emperor [http://books.google.es/books?id=DUwLAAAAIAAJ&lpg=PA716&dq=&pg=PA716#v=onepage&q=&f=false] [http://books.google.es/books?id=nPwQAAAAIAAJ&dq=&lr&as_brr=3&pg=PA182#v=onepage&q=&f=false]</ref>
    // 25 September 1506 –<br> 25 October 1555<ref>{{cite book|url=http://books.google.es/books?id=idjdQOYlK-4C&lpg=PA39&dq=&lr&as_brr=3&pg=PA39#v=onepage&q=&f=true |title=Abdication of Brussels |publisher=Books.google.es |accessdate=8 June 2012}}</ref>
    // {{nowrap|23 January 1516 – 16 January 1556}}
    // 4 November 1650<ref name=OSNS/>&nbsp;–<br > 8 March 1702
    // 13 February 1689 –<br > 8 March 1702
    // July 1672&nbsp;– 8 March 1702
    // 11/22{{ref|dates|O.S./N.S.}} June 1727&nbsp;–<br /> 25 October 1760
    // 1458–1490
    private void parseReign(Royalty royalty, Tuple2<String,Integer> data) {
        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        // TODO: do some preprocessing

        List<FlexibleDate> dates = dateParser.parseDateEnglishFormat(data.element1());
        dates.sort(new FlexibleDateComparator());
        succession.setFrom(dates.get(0));
        succession.setTo(dates.get(1));

    }

    private Royalty.Succession getCurrentSuccession(Royalty royalty, Integer num) {
        if (royalty.getSuccessions().size() <= num) {
            royalty.getSuccessions().add(new Royalty.Succession());
        }
        return royalty.getSuccessions().get(num);
    }
}
