package org.ib.history.db.neo4j.jdbc;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.Neo4jService;

import java.sql.SQLException;
import java.util.List;

public class Neo4jJdbcService implements Neo4jService {

    @Override
    public List<CountryDto> getCountries() {

        Neo4jJdbcTemplate template = new Neo4jJdbcTemplate();

        template.query("match (c:Country) return c", null);

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
