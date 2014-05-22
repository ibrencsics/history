package org.ib.history.db.neo4j.dao;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;

import java.util.List;
import java.util.Locale;

public interface CountryDao {
    List<LocalizedDto<CountryDto>> getCountries();
    LocalizedDto<CountryDto> getCountryById(Long id);
    LocalizedDto<CountryDto> getCountryByName(String name);
    LocalizedDto<CountryDto> getCountryByName(String name, Locale locale);
    CountryDto mergeById(CountryDto defaultCountry);
    CountryDto mergeByName(CountryDto defaultCountry);
    LocalizedDto<CountryDto> mergeLocale(CountryDto defaultCountry, CountryDto localeCountry, Locale locale);
    void deleteCountry(CountryDto country);
}
