package org.ib.history.db.neo4j.java.dao;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.ib.history.db.neo4j.java.Converters;
import org.ib.history.db.neo4j.java.ParamBuilder;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.logging.BufferingLogger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DefaultCountryDaoImpl implements CountryDao {

    private final GraphDatabaseService graphDatabaseService;

    @Autowired
    public DefaultCountryDaoImpl(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }

    private ExecutionEngine getExecutionEngine() {
        return new ExecutionEngine(graphDatabaseService, new BufferingLogger());
    }

    @Override
    public List<CountryDto> getCountries_() {
        String query = "match (c:Country) return c";
        ExecutionResult result = getExecutionEngine().execute(query);
        return Converters.getCountryListConverter().convert(result);
    }

//    @Override
//    public Long putCountry(CountryDto defaultCountry) {
//
//        ParamBuilder paramBuilder = new ParamBuilder();
//
////        Map<String,Object> params = new ParamBuilder()
////                .addParam("props", "name", defaultCountry.getName())
////                .build();
////        ExecutionResult result = getExecutionEngine().execute("create (c:Country {props}) return c", params);
//        return null;
//    }
//
//    @Override
//    public Long putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale) {
//        return null;
//    }

    @Override
    public void deleteCountry(CountryDto country) {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public List<LocalizedDto<CountryDto>> getCountries() {
        String query = "match (c:Country) optional match (c)-[l:TRANSLATION]->(cl) return c,l,cl";
        ExecutionResult result = getExecutionEngine().execute(query);
        return Converters.getLocalizedCountryListConverter().convert(result);
    }

    @Override
    public CountryDto mergeById(CountryDto defaultCountry) {
        Map<String,Object> params = new ParamBuilder()
                .addParam("id", defaultCountry.getId())
                .addParam("props", "name", defaultCountry.getName())
                .build();
        ExecutionResult result = getExecutionEngine().execute("match (c:Country) where id(c)={id} set c={props} return c", params);
        if (result.isEmpty()) {
            result = getExecutionEngine().execute("create (c:Country) set c={props} return c");
        }
        return Converters.getCountryConverter().convert(result);
    }

    @Override
    public CountryDto mergeByName(CountryDto defaultCountry) {
        Map<String,Object> params = new ParamBuilder()
                .addParam("name", defaultCountry.getName())
                .addParam("props", "name", defaultCountry.getName())
                .build();
        ExecutionResult result = getExecutionEngine().execute("merge (c:Country {name:{name}}) set c={props} return c", params);
        return Converters.getCountryConverter().convert(result);
    }

    public LocalizedDto<CountryDto> mergeLocale(CountryDto defaultCountry, CountryDto localeCountry, Locale locale) {
        Map<String,Object> params = new ParamBuilder()
                .addParam("name", defaultCountry.getName())
                .addParam("lang", locale.getLanguage().toUpperCase())
                .addParam("props", "name", localeCountry.getName())
                .build();

        ExecutionResult result = getExecutionEngine().execute(
                "match (c:Country {name:{name}}) " +
                "create (c)-[t:TRANSLATION{lang:{lang}}]->(l{props}) " +
                "return c,t,l",
                params);
        return Converters.getLocalizedCountryConverter().convert(result);
    }
}
