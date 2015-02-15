package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Country;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface CountryRepository extends GraphRepository<Country> {

//    @Query("match (n:Country{defaultLocale:true}) optional match (n)-[t]->(m) return n,t,m")
//    List<LocalizedCountry> getCountries();
//
//    @QueryResult
//    public interface LocalizedCountry {
//        @ResultColumn("n")
//        Country getCountry();
//
//        @ResultColumn("t")
//        Country.Translation getTranslation();
//
//        @ResultColumn("m")
//        Country getLocaleCountry();
//    }

    @Query("match (n:Country{defaultLocale:true}) return n")
    List<Country> getCountries();

    @Query("match (n:Country{defaultLocale:true}) return n order by n.name skip {0} limit {1}")
    List<Country> getCountries(int start, int length);

    @Query("match (n:Country{defaultLocale:true}) where n.name=~{0}  return n")
    List<Country> getCountriesByPattern(String pattern);
}
