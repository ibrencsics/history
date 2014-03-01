package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface CountryRepository extends GraphRepository<Country> {

    @Query("match (c:Country) return c.name")
    List<String> getCountries();
}
