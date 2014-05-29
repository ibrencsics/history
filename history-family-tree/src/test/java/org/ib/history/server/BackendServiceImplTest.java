package org.ib.history.server;

import org.ib.history.db.neo4j.Neo4jCountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:neo4j-config.xml" })
public class BackendServiceImplTest {

    @Autowired
    Neo4jCountryService neo4jCountryService;

    @Test
    public void test() {
//        System.setProperty("org.neo4j.rest.batch_transaction", "true");
        neo4jCountryService.getCountries();
    }
}
