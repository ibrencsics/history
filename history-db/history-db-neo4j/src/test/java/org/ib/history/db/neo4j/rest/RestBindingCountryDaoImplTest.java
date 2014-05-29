package org.ib.history.db.neo4j.rest;

import org.ib.history.db.neo4j.dao.CountryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class RestBindingCountryDaoImplTest {

    @Autowired
    @Qualifier("restBindingCountryDao")
    private CountryDao dao;

    @Test
//    @Transactional
    public void test() {
        System.out.println(dao.getCountries());
    }
}
