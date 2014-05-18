package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.*;
import org.ib.history.db.neo4j.Neo4jServiceDeprecated;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.*;

public class Neo4jJavaServiceDeprecated implements Neo4jServiceDeprecated {

    Neo4jJavaTemplate template;

    public Neo4jJavaServiceDeprecated() {
        this.template = new Neo4jJavaTemplate(new TestGraphDatabaseFactory().newImpermanentDatabase());
    }

    public Neo4jJavaServiceDeprecated(Neo4jJavaTemplate template) {
        this.template = template;
    }

    @Override
    public List<CountryDto> getCountries() {
        return template.executeQuery(Converters.getCountryListConverter(), "match (c:Country) return c");
    }

    @Override
    public List<CountryDto> getCountries(Locale locale) {
        Map<String,Object> params = new ParamBuilder().addParam("lang", locale.getLanguage().toUpperCase()).build();
        return template.executeQuery(Converters.getCountryListConverter(),
                "match (cd:Country)-[cr:TRANSLATION{lang:{lang}}]->(c) return id(cd), c",
                params);
    }

    @Override
    public void putCountry(CountryDto defaultCountry) {
        Map<String,Object> params = new ParamBuilder()
                .addParam("props", "name", defaultCountry.getName())
                .build();
        template.executeQuery(Converters.getCountryListConverter(), "create (c:Country {props}) return c", params);
    }

    @Override
    public void putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale) {
        Map<String,Object> params = new ParamBuilder()
                .addParam("name", defaultCountry.getName())
                .addParam("lang", locale.getLanguage().toUpperCase())
                .addParam("localeName", localeCountry.getName())
                .build();
        template.executeQuery(Converters.getCountryListConverter(),
                "match (c:Country) where c.name={name} " +
                "merge (c)-[cr:TRANSLATION{lang:{lang}}]->(ct {name:{localeName}}) " +
                "return c",
                params);
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
        Map<String,Object> params = new ParamBuilder().addParam("name", country.getName()).build();
        template.executeUpdate("match (c:Country {name:{name}})-[r]-() delete c,r", params);
    }

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

    @Override
    public List<RulerDto> getRulers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RulerDto> getRulers(Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putRuler(RulerDto ruler) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
