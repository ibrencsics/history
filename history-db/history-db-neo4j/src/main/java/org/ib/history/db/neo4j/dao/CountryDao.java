package org.ib.history.db.neo4j.dao;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;

import java.util.List;
import java.util.Locale;

public interface CountryDao {
    List<CountryDto> getCountries();
    List<CountryDto> getCountries(Locale locale);
    void putCountry(CountryDto defaultCountry);
    void putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale);
    void putCountry(LocalizedDto<CountryDto> country);
    void deleteCountry(CountryDto country);
}
