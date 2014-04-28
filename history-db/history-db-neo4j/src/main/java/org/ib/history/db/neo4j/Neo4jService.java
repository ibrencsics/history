package org.ib.history.db.neo4j;

import org.ib.history.commons.data.CountryDto;

import java.util.List;

public interface Neo4jService {
    List<CountryDto> getCountries();
}
