package org.ib.history.db.neo4j.rest;

import org.ib.history.commons.data.*;
import org.ib.history.db.neo4j.Converter;
import org.ib.history.db.neo4j.Neo4jService;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.util.QueryResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.*;

public class Neo4jRestService implements Neo4jService {

//    private static final Logger logger = LoggerFactory.getLogger(Neo4jRestService.class);
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Neo4jRestService.class);

    @Override
    public List<CountryDto> getCountries() {

        Neo4jRestTemplate template = new Neo4jRestTemplate();

        Converter<List<CountryDto>, QueryResult<Map<String, Object>>> converter = new Converter<List<CountryDto>, QueryResult<Map<String, Object>>>() {
            @Override
            public List<CountryDto> convert(QueryResult<Map<String, Object>> result) {

                List<CountryDto> countryList = new ArrayList<>();

                Iterator<Map<String, Object>> iter = result.iterator();
                while (iter.hasNext()) {
                    Map<String,Object> map = iter.next();

                    for (Map.Entry<String,Object> entry : map.entrySet()) {
                        System.out.println(entry.getKey() +  " : " + entry.getValue());

                        CountryDto county = new CountryDto();
                        countryList.add(county);

                        RestNode restNode = (RestNode) entry.getValue();
                        Iterable<String> props = restNode.getPropertyKeys();
                        Iterator<String> it = props.iterator();
                        while (it.hasNext()) {
                            String prop = it.next();
                            System.out.println("  " + prop + " : " + restNode.getProperty(prop));
                            if (prop=="name") county.setName(restNode.getProperty(prop).toString());
                        }
                    }
                }

                return countryList;
            }
        };

       LOG.info("asd");
       return template.executeQuery(converter, "match (c:Country) return c");
    }

    @Override
    public List<CountryDto> getCountries(Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putCountry(CountryDto defaultCountry) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void putCountry(LocalizedDto<CountryDto> country) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCountry(CountryDto country) {
        //To change body of implemented methods use File | Settings | File Templates.
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
