package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.Refdata;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
public class DefaultNeo4jCountryDaoImplTest {

    @Autowired
    private CountryDao dao;

    @Test
//    @Transactional
    public void getCountriesTest() {
        CountryDto countryDto = Refdata.getEngland().getDefaultLocaleElement();

        CountryDto mergedDto = null;

        if (countryDto.getId()!=null)
            mergedDto = dao.mergeById(countryDto);
        else
            mergedDto = dao.mergeByName(countryDto);

        System.out.println(dao.getCountries());

        if (mergedDto.getId()!=null)
            mergedDto = dao.mergeById(mergedDto.withName("byId"));
        else
            mergedDto = dao.mergeByName(mergedDto.withName("byName"));

        System.out.println(dao.getCountries());

        dao.mergeLocale(mergedDto, Refdata.getEngland().getLocales().get(new Locale("HU")), new Locale("HU"));
        dao.mergeLocale(mergedDto, Refdata.getEngland().getLocales().get(new Locale("DE")), new Locale("DE"));

        System.out.println(dao.getCountries());
    }
}
