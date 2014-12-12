package org.ib.history.wiki.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class RoyaltyParser {

    private TemplateParser templateParser = new TemplateParser();
    private DateParser dateParser = new DateParser();

    private Map<String,BiConsumer<Royalty,String>> parserMap = new HashMap<>(10);

    public RoyaltyParser() {
        parserMap.put("name", ((object, value) -> { object.setName(value); }));
        parserMap.put("birth_date", this::parseBirthDate);
        parserMap.put("death_date", this::parseDeathDate);
    }

    public Royalty parse(String wikiText) {
        Optional<String> template = templateParser.getTemplate(wikiText, "Infobox royalty");
        Map<String,String> data = templateParser.getTemplateDataMap(template.get());
        return parse(data);
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

    private void parseBirthDate(Royalty royalty, String data) {
        royalty.setDateOfBirth(dateParser.parse(data));
    }

    private void parseDeathDate(Royalty royalty, String data) {
        royalty.setDateOfDeath(dateParser.parse(data));
    }
}
