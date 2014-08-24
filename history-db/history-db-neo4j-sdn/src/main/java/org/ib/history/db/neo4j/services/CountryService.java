package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.CountryData;

import java.util.List;

public interface CountryService {
    List<CountryData> getCountries();
    List<CountryData> getCountriesByPatter(String pattern);
    CountryData addCountry(CountryData countryData);
    void deleteCountry(CountryData countryData);
}
