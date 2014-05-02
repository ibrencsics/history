package org.ib.history.db.neo4j.jdbc;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.EmpirorDto;
import org.ib.history.commons.data.LocalizedCountryDto;
import org.ib.history.db.neo4j.Converter;
import org.ib.history.db.neo4j.Neo4jService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Neo4jJdbcService implements Neo4jService {

    Neo4jJdbcTemplate template;

    public Neo4jJdbcService() {
        this.template = new Neo4jJdbcTemplate();
    }

    public Neo4jJdbcService(Neo4jJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<CountryDto> getCountries() {
        return template.executeQuery(new CountryListConverter(), "match (c:Country) return id(c), c.name");
    }

    @Override
    public List<CountryDto> getCountries(Locale locale) {
        return template.executeQuery(new CountryListConverter(),
                "match (c:Country)-[cr:TRANSLATION{lang:{1}}]->(ct) return id(c), ct.name",
                locale.getLanguage().toUpperCase());
    }

    @Override
    public void putCountry(Locale locale, CountryDto country) {
        if (locale.equals(DEFAULT_LOCALE)) {
            template.executeUpdate(
                    "merge (c:Country {name:{1}}) " +
                    "on create set c.created = timestamp() " +
                    "on match set c.accessed = timestamp()",
                    country.getName());
        } else {

        }
    }

    @Override
    public List<EmpirorDto> getEmpirors() {
        return template.executeQuery(new EmpirorListConverter(),
                "match (e:Empiror)-[r:RULES]->(c) return id(e), e.name, e.alias, r.from, r.to, c.name");
    }

    @Override
    public List<EmpirorDto> getEmpirors(Locale locale) {
        return template.executeQuery(new EmpirorListConverter(),
                "match " +
                        "(et)<-[er:TRANSLATION{lang:{1}}]-(e:Empiror)-[r:RULES]->(c)-[cr:TRANSLATION{lang:{1}}]->(ct) " +
                        "return id(e), et.name, et.alias, r.from, r.to, ct.name;",
                locale.getLanguage().toUpperCase());
    }

    /*******************************
     * Utils
     ******************************/

    private class CountryListConverter implements Converter<List<CountryDto>, ResultSet> {
        @Override
        public List<CountryDto> convert(ResultSet rs) {
            List<CountryDto> countries = new ArrayList<>();

            try {
                while (rs.next()) {
                    CountryDto country = new CountryDto();
                    country.setId(Long.parseLong(rs.getString(1)));
                    country.setName(rs.getString(2));
                    countries.add(country);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return countries;
        }
    }

    private class EmpirorListConverter implements Converter<List<EmpirorDto>, ResultSet> {
        @Override
        public List<EmpirorDto> convert(ResultSet rs) {
            List<EmpirorDto> empirors = new ArrayList<>();

            try {
                while (rs.next()) {
                    EmpirorDto empiror = new EmpirorDto();
                    empiror.setId(Long.parseLong(rs.getString(1)));
                    empiror.setName(rs.getString(2));
                    empiror.setAlias(rs.getString(3));
                    empiror.setFrom(rs.getString(4));
                    empiror.setTo(rs.getString(5));

                    CountryDto country = new CountryDto();
                    country.setName(rs.getString(6));
                    empiror.setCountryDto(country);

                    empirors.add(empiror);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return empirors;
        }
    }
}
