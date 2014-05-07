package org.ib.history.db.neo4j.jdbc;

import org.ib.history.commons.data.*;
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
        this.template = new Neo4jJdbcTemplate("jdbc:neo4j:mem");
//        this.template = new Neo4jJdbcTemplate("jdbc:neo4j://localhost:7474");
    }

    public Neo4jJdbcService(String url) {
        this.template = new Neo4jJdbcTemplate(url);
    }

    public Neo4jJdbcService(Neo4jJdbcTemplate template) {
        this.template = template;
    }


    /**
     * Country
     */

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
    public void putCountry(CountryDto defaultCountry) {
        template.executeUpdate(
                "merge (c:Country {name:{1}}) " +
                "on create set c.created = timestamp() " +
                "on match set c.accessed = timestamp()",
                defaultCountry.getName());
    }

    @Override
    public void putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale) {
        template.executeUpdate(
                "match (c:Country {name:{1}}) " +
                "merge (c)-[cr:TRANSLATION{lang:{2}}]->(ct {name:{3}}) " +
                "on create set ct.name = {3} " +
                "on match set ct.name = {3}",
                defaultCountry.getName(),
                locale.getLanguage().toUpperCase(),
                localeCountry.getName()
        );
    }

    @Override
    public void deleteCountry(CountryDto country) {
        template.executeUpdate(
                "match (c:Country {name:{1}})-[r]-() " +
                "delete c,r",
                country.getName()
        );
    }

    @Override
    public void putCountry(LocalizedDto<CountryDto> country) {
        putCountry(country.getDefaultLocaleElement());
        for (Map.Entry<Locale,CountryDto> entry : country.getLocales().entrySet()) {
            putCountry(country.getDefaultLocaleElement(), entry.getValue(), entry.getKey());
        }
    }

    /**
     * House
     */

    @Override
    public List<HouseDto> getHouses() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<HouseDto> getHouses(Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putHouse(HouseDto house) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putHouse(HouseDto defaultHouse, HouseDto localeHouse, Locale locale) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putHouse(LocalizedDto<HouseDto> house) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteHouse(HouseDto house) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Person
     */

    @Override
    public List<PersonDto> getPeople() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<PersonDto> getPeople(Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putPerson(PersonDto person) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putPerson(PersonDto defaultPerson, PersonDto localePerson, Locale locale) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putPerson(LocalizedDto<PersonDto> person) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deletePerson(PersonDto person) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Ruler
     */

    @Override
    public List<RulerDto> getRulers() {
        return template.executeQuery(new RulerListConverter(),
                "match (r:Ruler)-[rr:RULES]->(c) return id(r), r.name, r.alias, rr.from, rr.to, c.name");
    }

    @Override
    public List<RulerDto> getRulers(Locale locale) {
        return template.executeQuery(new RulerListConverter(),
                "match " +
                        "(rt)<-[rrt:TRANSLATION{lang:{1}}]-(r:Ruler)-[rr:RULES]->(c)-[cr:TRANSLATION{lang:{1}}]->(ct) " +
                        "return id(r), rt.name, rt.alias, rr.from, rr.to, ct.name;",
                locale.getLanguage().toUpperCase());
    }

    @Override
    public void putRuler(RulerDto ruler) {
        template.executeUpdate(
                "merge (e:Ruler {name:{1}})-[] " +
                        "on create set c.created = timestamp() " +
                        "on match set c.accessed = timestamp()",
                ruler.getName());
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

    private class RulerListConverter implements Converter<List<RulerDto>, ResultSet> {
        @Override
        public List<RulerDto> convert(ResultSet rs) {
            List<RulerDto> rulers = new ArrayList<>();

            try {
                while (rs.next()) {
                    RulerDto ruler = new RulerDto();
                    ruler.setId(Long.parseLong(rs.getString(1)));
                    ruler.setName(rs.getString(2));
                    ruler.setAlias(rs.getString(3));
                    ruler.setFrom(rs.getString(4));
                    ruler.setTo(rs.getString(5));

                    CountryDto country = new CountryDto();
                    country.setName(rs.getString(6));
                    ruler.setCountryDto(country);

                    rulers.add(ruler);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rulers;
        }
    }
}
