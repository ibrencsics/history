package org.ib.history.db.neo4j.rest.dao;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.ib.history.db.neo4j.rest.Converters;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RestBindingCountryDao implements CountryDao {

    private final RestGraphDatabase graphDatabaseService;
    private RestCypherQueryEngine engine;

    @Autowired
    public RestBindingCountryDao(RestGraphDatabase graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
        this.engine = new RestCypherQueryEngine(graphDatabaseService.getRestAPI());
    }


    @Override
    public List<LocalizedDto<CountryDto>> getCountries() {

        try ( Transaction tx = graphDatabaseService.beginTx() ) {
            String query = "match (c:Country) optional match (c)-[r:TRANSLATION]->(t) return c,r,t";
            QueryResult<Map<String, Object>> result = engine.query(query, new HashMap<String, Object>());
    //        return Converters.getCountryListConverter().convert(result);
            tx.success();
            return null;
        }
    }

    @Override
    public LocalizedDto<CountryDto> getCountryById(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LocalizedDto<CountryDto> getCountryByName(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LocalizedDto<CountryDto> getCountryByName(String name, Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CountryDto mergeById(CountryDto defaultCountry) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CountryDto mergeByName(CountryDto defaultCountry) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LocalizedDto<CountryDto> mergeLocale(CountryDto defaultCountry, CountryDto localeCountry, Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteCountry(CountryDto country) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
