package org.ib.history.wiki.parser;

import com.google.common.collect.ImmutableMap;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.tuples.Tuple2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class WikiParser {

    public RoyaltyInfobox getRoyaltyInfobox(String wikiText) throws IOException {
        final String plain = getRoyaltyInfoboxText(wikiText);
        return parse(plain);
    }

    String getRoyaltyInfoboxText(String wikiText) {
        final String startStr = "{{Infobox royalty";
        int start = wikiText.indexOf(startStr) + startStr.length();
        int stop = wikiText.indexOf("|}}", start);
        return wikiText.substring(start, stop);
    }

    RoyaltyInfobox parse(String infoboxPlain) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(infoboxPlain));

        RoyaltyInfobox ret = new RoyaltyInfobox();

        String line;
        while ( (line=br.readLine()) != null) {
            System.out.println("line: " + line);
            if (line.length() < 2) continue;


            final String[] tokens = line.substring(2).split("=");
            final String key = tokens[0].trim();
            final String value = tokens[1].trim();

            final String parserKey = key.replaceAll("\\d*$", "");

//            Tuple2<String, Integer> parserValue;
//            if (parserKey.length() < key.length()) {
//                String indexStr = key.substring(parserKey.length());
//                parserValue = new Tuple2<>(value, Integer.valueOf(indexStr));
//            } else {
//                parserValue = new Tuple2<>(value, null);
//            }

            BiConsumer<RoyaltyInfobox,String> parser = parserMap.get(parserKey);
            if (parser!=null) {
                parser.accept(ret, value);
            }
        }

        return ret;
    }

    private static Map<String,BiConsumer<RoyaltyInfobox,String>> parserMap = ImmutableMap.of(
            "name", ((object, value) -> { object.setName(value); }),
            "succession", WikiParser::processSuccession,
            "reign", WikiParser::provessReign
    );

    private static void processSuccession(RoyaltyInfobox object, String value) {
        RoyaltyInfobox.Succession succession = new RoyaltyInfobox.Succession();
        object.getSuccessions().add(succession);

        String[] titleAndCountries = parseLinkAndValue(value).element2().split("of");
        String title = titleAndCountries[0].trim();
        String countries = titleAndCountries[1].trim();

        succession.setTitle(title);
        succession.getCountries().addAll(Arrays.asList(countries.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList()));
    }

    private static void provessReign(RoyaltyInfobox object, String value) {
        RoyaltyInfobox.Succession succession = object.getSuccessions().get(object.getSuccessions().size()-1);
        succession.setFrom(new FlexibleDate.Builder().year(2133).build());
    }

    private static Tuple2<String,String> parseLinkAndValue(String raw) {
        String[] linkAndValue = raw.replaceAll("\\[", "").replaceAll("\\]", "").split("\\|");
        return new Tuple2<>(linkAndValue[0], linkAndValue[1]);
    }

    // [[Principality of Orange|Prince of Orange]]
    // [[Stadtholder|Stadtholder of Holland, Zeeland, Utrecht, Gelderland and Overijssel]]
    // [[List of English monarchs|King of England]], [[List of Scottish monarchs|Scotland]] and [[King of Ireland|Ireland]]
    private static List<Tuple2<String,String>> parseReferenceList(String raw) {
        final List<Tuple2<String,String>> ret = new ArrayList<>();

        return ret;
    }
}
