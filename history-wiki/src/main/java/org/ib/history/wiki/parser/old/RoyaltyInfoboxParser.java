package org.ib.history.wiki.parser.old;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.tuples.Tuple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class RoyaltyInfoboxParser {

    private Map<String,BiConsumer<RoyaltyInfobox,String>> parserMap = new HashMap<>(10);

    public RoyaltyInfoboxParser() {
        parserMap.put("name", ((object, value) -> { object.setName(value); }));
        parserMap.put("succession", this::processSuccession);
        parserMap.put("reign", this::processReign);
    }

    RoyaltyInfobox parse(String wikiText) throws IOException {
        String infoboxPlain = getRoyaltyInfoboxText(wikiText);

        BufferedReader br = new BufferedReader(new StringReader(infoboxPlain));

        RoyaltyInfobox ret = new RoyaltyInfobox();

        String line;
        while ( (line=br.readLine()) != null) {
            System.out.println("line: " + line);
            if (line.length() < 2) continue;


            final String[] tokens = line.substring(2).split("=");
            if (tokens.length == 2) {
                final String key = tokens[0].trim();
                final String value = tokens[1].trim();
                // line: | name = William III

                final String parserKey = key.replaceAll("\\d*$", "");
//                System.out.println(parserKey);

                BiConsumer<RoyaltyInfobox, String> parser = parserMap.get(parserKey);
                if (parser != null) {
                    parser.accept(ret, value);
                }
            }
        }

        return ret;
    }

    private String getRoyaltyInfoboxText(String wikiText) {
        final String startStr = "{{Infobox royalty";
        int start = wikiText.indexOf(startStr) + startStr.length();
        int stop = wikiText.indexOf("|}}", start);
        return wikiText.substring(start, stop);
    }

    // Succession

    private void processSuccession(RoyaltyInfobox object, String value) {
        RoyaltyInfobox.Succession succession = new RoyaltyInfobox.Succession();
        object.getSuccessions().add(succession);

        Tuple2<String,List<String>> parsed = parseReferenceList(value);
        succession.setTitle(parsed.element1());
        succession.getCountries().addAll(parsed.element2());
    }

    Tuple2<String,List<String>> parseReferenceList(String raw) {

        List<Tuple2<String,String>> tags = parseTags(raw);
        String sentence = tagsToSentence(tags);
        List<String> words = sentenceToWords(sentence);
        List<String> importantWords = eliminateWords(words);

        Tuple2<String,List<String>> parsed = new Tuple2<>(importantWords.get(0), importantWords.subList(1, importantWords.size()));
        return parsed;
    }

    List<String> eliminateWords(List<String> words) {
        return words.stream().filter(this::isImportantWord).collect(Collectors.toList());
    }

    boolean isImportantWord(String word) {
        List<String> nonImportants = Arrays.asList("and", "of");
        return !nonImportants.contains(word);
    }

    List<String> sentenceToWords(String sentence) {
        String[] words = sentence.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        return Arrays.asList(words);
    }

    String tagsToSentence(List<Tuple2<String,String>> tags) {
        return tags.stream().map(t -> t.element2()).collect(Collectors.joining(", "));
    }

    List<Tuple2<String,String>> parseTags(String raw) {
        return getTags(raw).stream().map(this::parseTag).collect(Collectors.toList());
    }

    Tuple2<String,String> parseTag(String tag) {
        String[] tokens = tag.split("\\|");
        return new Tuple2<String,String>(tokens[0], tokens[1]);
    }

    List<String> getTags(String raw) {
        List<String> ret = new ArrayList<>(3);

        int tagStart = -1;
        int tagEnd = -1;

        for (int i=1; i<raw.length(); i++) {
            char c1 = raw.charAt(i-1);
            char c2 = raw.charAt(i);

            if (c1=='[' && c2=='[') {
                tagStart = i+1;
            }
            else if (c1==']' && c2==']') {
                tagEnd = i-1;

                String tag = raw.substring(tagStart, tagEnd);
                ret.add(tag);
            }
        }

        return ret;
    }

    // Reign

    private void processReign(RoyaltyInfobox object, String value) {
        RoyaltyInfobox.Succession succession = object.getSuccessions().get(object.getSuccessions().size()-1);
        succession.setFrom(new FlexibleDate.Builder().year(2133).build());
    }
}
