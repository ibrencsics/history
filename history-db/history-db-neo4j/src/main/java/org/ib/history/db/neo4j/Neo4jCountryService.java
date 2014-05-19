package org.ib.history.db.neo4j;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;

import java.util.List;
import java.util.Locale;

public interface Neo4jCountryService {
    List<LocalizedDto<CountryDto>> getCountries();
    LocalizedDto<CountryDto> getCountryById(Long id);
    LocalizedDto<CountryDto> getCountryByName(String name);
    LocalizedDto<CountryDto> getCountryByName(String name, Locale locale);
    Long putCountry(LocalizedDto<CountryDto> country);
    void deleteCountry(LocalizedDto<CountryDto> country);
}
