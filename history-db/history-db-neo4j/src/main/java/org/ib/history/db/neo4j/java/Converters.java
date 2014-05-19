package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.Converter;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.Node;
import scala.collection.Iterator;
import scala.collection.immutable.Map;

import java.util.ArrayList;
import java.util.List;

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
                List<LocalizedDto<CountryDto>> countries = new ArrayList<>();


                return countries;
            }
        };
    }

    public static Converter<LocalizedDto<CountryDto>, ExecutionResult> getLocalizedCountryConverter() {
        return new Converter<LocalizedDto<CountryDto>, ExecutionResult>() {
            @Override
            public LocalizedDto<CountryDto> convert(ExecutionResult source) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }
}
