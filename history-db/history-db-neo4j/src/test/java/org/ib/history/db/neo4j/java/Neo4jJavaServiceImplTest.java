package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.Neo4jService;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class Neo4jJavaServiceImplTest {

    @Autowired
    private Neo4jService service;

    @Test
    public void getCountriesTest() {
        service.putCountry(new CountryDto().withName("England"));
        System.out.println(service.getCountries());
    }
}
