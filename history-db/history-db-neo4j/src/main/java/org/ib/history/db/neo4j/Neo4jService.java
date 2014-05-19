package org.ib.history.db.neo4j;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.LocalizedDto;

import java.util.List;
import java.util.Locale;

public interface Neo4jService {
    List<CountryDto> getCountries();
    void putCountry(CountryDto defaultCountry);
}
