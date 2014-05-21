package org.ib.history.db.neo4j.java;

import org.ib.history.db.neo4j.Neo4jCountryService;
import org.ib.history.db.neo4j.Refdata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class DefaultNeo4jCountryServiceImplTest {

    @Autowired
    private Neo4jCountryService service;

    @Test
    public void test() {
        service.putCountry(Refdata.getHungary());
        System.out.println(service.getCountries());
        service.putCountry(Refdata.getEngland());
        System.out.println(service.getCountries());
    }
}
