package org.ib.history.db.neo4j.jdbc;

import org.ib.history.commons.data.*;
import org.ib.history.commons.utils.Neo4jDateFormat;
import org.ib.history.db.neo4j.Converter;
import org.ib.history.db.neo4j.Neo4jServiceDeprecated;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Neo4jJdbcService implements Neo4jServiceDeprecated {

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
    public void putCountry(LocalizedDto<CountryDto> country) {
        putCountry(country.getDefaultLocaleElement());
        for (Map.Entry<Locale,CountryDto> entry : country.getLocales().entrySet()) {
            putCountry(country.getDefaultLocaleElement(), entry.getValue(), entry.getKey());
        }
    }

    @Override
    public void deleteCountry(CountryDto country) {
        template.executeUpdate(
                "match (c:Country {name:{1}})-[r]-() " +
                "delete c,r",
                country.getName()
        );
    }

    /**
     * House
     */

    @Override
    public List<HouseDto> getHouses() {
        return template.executeQuery(new HouseListConverter(), "match (h:House) return id(h), h.name");
    }

    @Override
    public List<HouseDto> getHouses(Locale locale) {
        return template.executeQuery(new HouseListConverter(),
                "match (h:House)-[hr:TRANSLATION{lang:{1}}]->(ht) return id(h), ht.name",
                locale.getLanguage().toUpperCase());
    }

    @Override
    public void putHouse(HouseDto defaultHouse) {
        template.executeUpdate(
                "merge (h:House {name:{1}}) " +
                        "on create set h.created = timestamp() " +
                        "on match set h.accessed = timestamp()",
                defaultHouse.getName());
    }

    @Override
    public void putHouse(HouseDto defaultHouse, HouseDto localeHouse, Locale locale) {
        template.executeUpdate(
                "match (h:House {name:{1}}) " +
                        "merge (h)-[hr:TRANSLATION{lang:{2}}]->(ht {name:{3}}) " +
                        "on create set ht.name = {3} " +
                        "on match set ht.name = {3}",
                defaultHouse.getName(),
                locale.getLanguage().toUpperCase(),
                localeHouse.getName()
        );
    }

    @Override
    public void putHouse(LocalizedDto<HouseDto> house) {
        putHouse(house.getDefaultLocaleElement());
        for (Map.Entry<Locale,HouseDto> entry : house.getLocales().entrySet()) {
            putHouse(house.getDefaultLocaleElement(), entry.getValue(), entry.getKey());
        }
    }

    @Override
    public void deleteHouse(HouseDto house) {
        template.executeUpdate(
                "match (h:House {name:{1}})-[r]-() " +
                        "delete h,r",
                house.getName()
        );
    }

    /**
     * Person
     */

    @Override
    public List<PersonDto> getPeople() {
        return template.executeQuery(new PersonListConverter(),
                "match (p:Person) return id(p), p.name, p.birth, p.death");
    }

    @Override
    public List<PersonDto> getPeople(Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putPerson(PersonDto defaultPerson) {
        Neo4jDateFormat neo4jDateFormat = new Neo4jDateFormat();

        template.executeUpdate(
                "merge (p:Person {name:{1}}) " +
                        "on create set p.birth = {2}, p.death = {3}",
                defaultPerson.getName(),
                neo4jDateFormat.serialize(defaultPerson.getDateOfBirth()),
                neo4jDateFormat.serialize(defaultPerson.getDateOfDeath()));
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

    private class HouseListConverter implements Converter<List<HouseDto>, ResultSet> {
        @Override
        public List<HouseDto> convert(ResultSet rs) {
            List<HouseDto> houses = new ArrayList<>();

            try {
                while (rs.next()) {
                    HouseDto house = new HouseDto();
                    house.setId(Long.parseLong(rs.getString(1)));
                    house.setName(rs.getString(2));
                    houses.add(house);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return houses;
        }
    }

    private class PersonListConverter implements Converter<List<PersonDto>, ResultSet> {
        @Override
        public List<PersonDto> convert(ResultSet rs) {
            List<PersonDto> people = new ArrayList<>();
            Neo4jDateFormat neo4jDateFormat = new Neo4jDateFormat();

            try {
                while (rs.next()) {
                    System.out.println(rs.getString(1)+":"+rs.getString(2)+":"+rs.getString(3)+":"+rs.getString(4));
                    PersonDto person = new PersonDto();
                    person.setId(Long.parseLong(rs.getString(1)));
                    person.setName(rs.getString(2));
                    person.setDateOfBirth(neo4jDateFormat.parseFull(rs.getString(3)));
                    person.setDateOfDeath(neo4jDateFormat.parseFull(rs.getString(4)));
                    people.add(person);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return people;
        }
    }
}
