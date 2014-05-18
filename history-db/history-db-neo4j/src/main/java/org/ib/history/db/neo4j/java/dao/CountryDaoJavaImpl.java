package org.ib.history.db.neo4j.java.dao;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.ib.history.db.neo4j.java.Converters;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.logging.BufferingLogger;

import java.util.List;
import java.util.Locale;

public class CountryDaoJavaImpl implements CountryDao {

    private final GraphDatabaseService graphDatabaseService;
    private final ExecutionEngine executionEngine;

    public CountryDaoJavaImpl(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
        this.executionEngine = new ExecutionEngine(graphDatabaseService, new BufferingLogger());
    }

    @Override
    public List<CountryDto> getCountries() {
        String query = "match (c:Country) return c";
        ExecutionResult result = executionEngine.execute(query);
        return Converters.getCountryListConverter().convert(result);
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
}
