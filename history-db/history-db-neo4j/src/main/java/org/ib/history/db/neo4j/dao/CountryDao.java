package org.ib.history.db.neo4j.dao;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;

import java.util.List;
import java.util.Locale;

public interface CountryDao {
    List<CountryDto> getCountries_();
    void deleteCountry(CountryDto country);

    List<LocalizedDto<CountryDto>> getCountries();
//    Long putCountry(CountryDto defaultCountry);
//    Long putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale);

    CountryDto mergeById(CountryDto defaultCountry);
    CountryDto mergeByName(CountryDto defaultCountry);
    LocalizedDto<CountryDto> mergeLocale(CountryDto defaultCountry, CountryDto localeCountry, Locale locale);
}
