package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.Converter;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import scala.collection.Iterator;

import java.util.*;

public class Converters {

    public static Converter<List<CountryDto>,ExecutionResult> getCountryListConverter() {
        return new Converter<List<CountryDto>, ExecutionResult>() {
            @Override
            public List<CountryDto> convert(ExecutionResult result) {
                List<CountryDto> countries = new ArrayList<>();

                Iterator<Node> neoCountries = result.columnAs("c");
                while (neoCountries.hasNext()) {
                    Node neoCountry = neoCountries.next();

                    CountryDto country = new CountryDto();
                    countries.add(country);
                    country.setId(neoCountry.getId());
                    country.setName((String) neoCountry.getProperty("name"));

//                    System.out.println("Country #" + neoCountry.getId());
//                    for (String propertyKey : neoCountry.getPropertyKeys()) {
//                        System.out.println("\t" + propertyKey + " : " + neoCountry.getProperty(propertyKey));
//                    }
                }
                return countries;
            }
        };
    }

    public static Converter<CountryDto,ExecutionResult> getCountryConverter() {
        return new Converter<CountryDto, ExecutionResult>() {
            @Override
            public CountryDto convert(ExecutionResult result) {
                Iterator<Node> neoCountries = result.columnAs("c");
                if (neoCountries.hasNext()) {
                    Node neoCountry = neoCountries.next();

                    CountryDto country = new CountryDto();
                    country.setId(neoCountry.getId());
                    country.setName((String) neoCountry.getProperty("name"));
                    return country;
                }
                return null;
            }
        };
    }

    public static Converter<List<LocalizedDto<CountryDto>>, ExecutionResult> getLocalizedCountryListConverter() {
        return new Converter<List<LocalizedDto<CountryDto>>, ExecutionResult>() {
            @Override
            public List<LocalizedDto<CountryDto>> convert(ExecutionResult result) {
                return getCountries(result);
            }
        };
    }

    public static Converter<LocalizedDto<CountryDto>, ExecutionResult> getLocalizedCountryConverter() {
        return new Converter<LocalizedDto<CountryDto>, ExecutionResult>() {
            @Override
            public LocalizedDto<CountryDto> convert(ExecutionResult result) {
                return getCountries(result).get(0);
            }
        };
    }

    private static List<LocalizedDto<CountryDto>> getCountries(ExecutionResult result) {
        List<LocalizedDto<CountryDto>> countries = new ArrayList<>();

        Map<String,LocalizedDto<CountryDto>> lookupTable = new HashMap<>();

        ResourceIterator<Map<String, Object>> iter = result.javaIterator();
        while (iter.hasNext()) {
            Map<String,Object> entry = iter.next();

            Node countryNode = (Node) entry.get("c");
            Relationship relation = (Relationship) entry.get("r");
            Node translationNode = (Node) entry.get("t");


            String countryName = countryNode.getProperty("name").toString();
            LocalizedDto<CountryDto> country = lookupTable.get(countryName);
            if (country == null) {
                country = new LocalizedDto<>();
                country.setDefaultLocaleElement(new CountryDto().withId(countryNode.getId()).withName(countryName));
                lookupTable.put(countryName, country);
                countries.add(country);
            }

            country.addLocaleElement(
                    new CountryDto().withName(translationNode.getProperty("name").toString()),
                    new Locale(relation.getProperty("lang").toString()));
        }

        return countries;
    }
}
