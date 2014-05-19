package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.Neo4jService;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class Neo4jJavaServiceImpl implements Neo4jService {

    @Autowired
    private CountryDao countryDao;

    @Override
    @Transactional
    public List<CountryDto> getCountries() {
        return countryDao.getCountries_();
    }

    @Override
    @Transactional
    public void putCountry(CountryDto defaultCountry) {
//        countryDao.putCountry(defaultCountry);
    }
}
