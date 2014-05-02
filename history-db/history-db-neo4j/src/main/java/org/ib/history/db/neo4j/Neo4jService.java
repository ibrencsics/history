package org.ib.history.db.neo4j;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.EmpirorDto;
import org.ib.history.commons.data.LocalizedCountryDto;

import java.util.List;
import java.util.Locale;

public interface Neo4jService {

    public static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    List<CountryDto> getCountries();
    List<CountryDto> getCountries(Locale locale);
    void putCountry(Locale locale, CountryDto country);

    List<EmpirorDto> getEmpirors();
    List<EmpirorDto> getEmpirors(Locale locale);
}
