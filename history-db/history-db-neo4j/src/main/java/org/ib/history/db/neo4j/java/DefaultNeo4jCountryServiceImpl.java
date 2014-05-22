package org.ib.history.db.neo4j.java;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;
import org.ib.history.db.neo4j.Neo4jCountryService;
import org.ib.history.db.neo4j.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DefaultNeo4jCountryServiceImpl implements Neo4jCountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    @Transactional
    public List<LocalizedDto<CountryDto>> getCountries() {
        return countryDao.getCountries();
    }

    @Override
    @Transactional
    public LocalizedDto<CountryDto> getCountryById(Long id) {
        return countryDao.getCountryById(id);
    }

    @Override
    @Transactional
    public LocalizedDto<CountryDto> getCountryByName(String name) {
        return countryDao.getCountryByName(name);
    }

    @Override
    @Transactional
    public LocalizedDto<CountryDto> getCountryByName(String name, Locale locale) {
        return countryDao.getCountryByName(name, locale);
    }

    @Override
    @Transactional
    public Long putCountry(LocalizedDto<CountryDto> country) {
        if (country.getId() != null) {
            countryDao.mergeById(country.getDefaultLocaleElement());
        } else {
            countryDao.mergeByName(country.getDefaultLocaleElement());
        }
        for (Map.Entry<Locale,CountryDto> locale : country.getLocales().entrySet()) {
            countryDao.mergeLocale(country.getDefaultLocaleElement(), locale.getValue(), locale.getKey());
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteCountry(LocalizedDto<CountryDto> country) {
        countryDao.deleteCountry(country.getDefaultLocaleElement());
    }
}
