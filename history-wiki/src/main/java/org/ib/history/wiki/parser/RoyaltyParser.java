package org.ib.history.wiki.parser;

import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.FlexibleDateComparator;
import org.ib.history.wiki.domain.WikiNamedResource;
import org.ib.history.commons.tuples.Tuple2;
import org.ib.history.wiki.domain.Royalty;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoyaltyParser {

    private static Logger logger = LogManager.getLogger(RoyaltyParser.class);

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
        logger.debug("Parsing [{}]", page);
        logger.trace("Wikitext [{}] : {}", page, wikiText);

        Optional<String> template = templateParser.getTemplate(wikiText, "Infobox royalty", "infobox nobility");
        logger.debug(template.get());
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
                logger.debug("to parse {}: {}", entry.getKey(), entry.getValue());
                parser.accept(royalty, new Tuple2<String, Integer>(entry.getValue(), seqNum));
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

    private void parseSuccession(Royalty royalty, Tuple2<String,Integer> data) {
        if (data.element1().isEmpty())
            return;

        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        String dataText = data.element1();

        List<WikiNamedResource> links = templateParser.getLinks(dataText);
        succession.setSuccessionLinks(links);
        succession.setSuccessionRaw(dataText);
//        succession.setSuccessionNoLinks(templateParser.removeLinks(dataText));
//        succession.setSuccessionNoLinksNoSmall(
//                templateParser.removeTemplate(
//                        templateParser.removeTemplate(
//                                templateParser.removeLinks(dataText), "small"), "br"));
        succession.setSuccessionNoLinksNoSmall(templateParser.removeLinks( templateParser.removeTags(dataText, "small", "br") ));
    }

    private void parseReign(Royalty royalty, Tuple2<String,Integer> data) {
        if (data.element1().isEmpty())
            return;

        Royalty.Succession succession = getCurrentSuccession(royalty, data.element2());

        // TODO: do some preprocessing
        String raw = templateParser.removeRef(data.element1());

        List<FlexibleDate> dates = dateParser.parseDateEnglishFormat(raw);
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
        int currentSize = royalty.getSuccessions().size();
        if (royalty.getSuccessions().size() <= num) {
            for (int i=0; i <= (num - currentSize + 1); i++) {
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
        royalty.getIssues().addAll(doListPostProcessing(links));
    }

    private void parseHouse(Royalty royalty, Tuple2<String,Integer> data) {
        List<WikiNamedResource> links = templateParser.getLinks(data.element1());
        royalty.getHouses().addAll(links);
    }

    private List<WikiNamedResource> doListPostProcessing(List<WikiNamedResource> in) {
        return in.stream()
                .filter(s -> !s.equals(new WikiNamedResource("illegitimate")))
                .collect(Collectors.toList());
    }
}
