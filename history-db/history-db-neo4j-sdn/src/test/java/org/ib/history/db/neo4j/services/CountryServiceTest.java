package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.AbstractIntegrationTest;
import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.domain.CountryRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;

import java.util.Iterator;

public class CountryServiceTest extends AbstractIntegrationTest {

    @Autowired
    CountryRepository countryRepository;

    @Test
    public void test() {
        EndResult<Country> countries = countryRepository.findAll();

        Iterator<Country> it = countries.iterator();

        while (it.hasNext()) {
            Country country = it.next();
            System.out.println(country.getName());
        }
    }

}
