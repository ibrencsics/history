package org.ib.history.wiki.parser;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.FlexibleDateComparator;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.wiki.domain.Royalty;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoyaltyParser {

    private TemplateParser templateParser = new TemplateParser();
    private DateParser dateParser = new DateParser();

    private Map<String,BiConsumer<Royalty,Tuple2<String,Integer>>> parserMap = new HashMap<>(15);

    public RoyaltyParser() {
        parserMap.put("name", this::parseName);
        parserMap.put("birth_date", this::parseBirthDate);
        parserMap.put("death_date", this::parseDeathDate);
        parserMap.put("father", this::parseFather);
        parserMap.put("mother", this::parseMother);
        parserMap.put("succession", this::parseSuccession);
        parserMap.put("reign", this::parseReign);
        parserMap.put("spouse", this::parseSpouse);
        parserMap.put("spouses", this::parseSpouse);
        parserMap.put("issue", this::parseIssue);
        parserMap.put("house", this::parseHouse);
        parserMap.put("predecessor", this::parsePredecessor);
        parserMap.put("successor", this::parseSuccessor);
    }

    public Royalty parse(String page) {
        MediaWikiBot mediaWikiBot = new MediaWikiBot( "http://en.wikipedia.org/w/" );
        Article article = mediaWikiBot.getArticle(page);
        String wikiText = article.getSimpleArticle().getText();

        if (wikiText.startsWith("#REDIRECT")) {
            List<WikiNamedResource> links = templateParser.getLinks(wikiText);
            article = mediaWikiBot.getArticle(links.get(0).getLocalPart());
            wikiText = article.getSimpleArticle().getText();
        }

        return parse(page, wikiText);
    }

    public Royalty parse(String page, String wikiText) {
        Optional<String> template = templateParser.getTemplate(wikiText, "Infobox royalty");
        Map<String,String> data = templateParser.getTemplateDataMap(template.get());
        return parse(page, data);
    }

    public Royalty parse(String page, Map<String,String> data) {
        Royalty royalty = new Royalty();
        royalty.setArticleName(page);

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
//                System.out.println("to parse " + entry.getKey() + ": " + entry.getValue());
                parser.accept(royalty, new Tuple2<String,Integer>(entry.getValue(), seqNum));
            }
            else {
//                System.out.println("no parsing " + entry.getKey());
            }
        }

        return royalty;
    }

    private void parseName(Royalty royalty, Tuple2<String,Integer> data) {
        royalty.setName(data.element1());
    }

    private void parseBirthDate(Royalty royalty, Tuple2<String,Integer> data) {
        royalty.setDateOfBirth(dateParser.parse(data.element1()));
    }

    private void parseDeathDate(Royalty royalty, Tuple2<String,Integer> data) {
        royalty.setDateOfDeath(dateParser.parse(data.element1()));
    }

    private void parseFather(Royalty royalty, Tuple2<String,Integer> data) { royalty.setFather(templateParser.parseLink(data.element1())); }

    private void parseMother(Royalty royalty, Tuple2<String,Integer> data) { royalty.setMother(templateParser.parseLink(data.element1())); }

    // [[King of Great Britain]] [[King of Ireland|and Iyreland]]<br />[[Electorate of Brunswick-Lüneburg|Elector of Hanover]]
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
        if (data.element1().isEmpty())
            return;

        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        String dataText = data.element1();

        // TODO: better preprocessing
        if (dataText.contains("small")) {
            dataText = dataText.substring(0, dataText.indexOf("small"));
        }

        List<WikiNamedResource> links = templateParser.getLinks(dataText);
        succession.setCountries(links);
        succession.setCountriesRaw(dataText);
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
        if (data.element1().isEmpty())
            return;

        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        // TODO: do some preprocessing

        List<FlexibleDate> dates = dateParser.parseDateEnglishFormat(data.element1());
        dates.sort(new FlexibleDateComparator());

        if (dates.size()>0)
            succession.setFrom(dates.get(0));

        if (dates.size()>1)
            succession.setTo(dates.get(1));

    }

    private void parsePredecessor(Royalty royalty, Tuple2<String,Integer> data) {
        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        // TODO: only the first link processed
        List<WikiNamedResource> links = templateParser.getLinks(data.element1());
        if (links.size()>0) {
            succession.setPredecessor(links.get(0));
        }
    }

    private void parseSuccessor(Royalty royalty, Tuple2<String,Integer> data) {
        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        List<WikiNamedResource> links = templateParser.getLinks(data.element1());
        if (links.size()>0) {
            succession.setSuccessor(links.get(0));
        }
    }

    private Royalty.Succession getCurrentSuccession(Royalty royalty, Integer num) {
        if (royalty.getSuccessions().size() <= num) {
            for (int i=0; i <= (num - royalty.getSuccessions().size() + 1); i++) {
                royalty.getSuccessions().add(new Royalty.Succession());
            }
        }
        return royalty.getSuccessions().get(num);
    }

    private void parseSpouse(Royalty royalty, Tuple2<String,Integer> data) {
        List<WikiNamedResource> links = templateParser.getLinks(data.element1());
        royalty.getSpouses().addAll(links);
    }

    private void parseIssue(Royalty royalty, Tuple2<String,Integer> data) {
        List<WikiNamedResource> links = templateParser.getLinks(data.element1());
        royalty.getIssues().addAll(links);
    }

    private void parseHouse(Royalty royalty, Tuple2<String,Integer> data) {
        List<WikiNamedResource> links = templateParser.getLinks(data.element1());
        royalty.getHouses().addAll(links);
    }
}
