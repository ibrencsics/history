package org.ib.history.db.neo4j.jdbc;

import org.junit.Test;

public class Neo4jJdbcServiceTest {

    @Test
    public void testGetCountries() {

        Neo4jJdbcService service = new Neo4jJdbcService();
        service.getCountries();
    }
}
