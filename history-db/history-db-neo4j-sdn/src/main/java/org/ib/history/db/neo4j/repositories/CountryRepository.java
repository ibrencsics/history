package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Country;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface CountryRepository extends GraphRepository<Country> {

    @Query("match (n:Country{defaultLocale:true}) optional match (n)-[t]->(m) return n,t,m")
    List<String> getCountries();
}
