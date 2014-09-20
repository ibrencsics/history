package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.House;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface HouseRepository extends GraphRepository<House> {

    @Query("match (n:House{defaultLocale:true}) return n")
    List<House> getHouses();

//    @Query("match (n:House{defaultLocale:true}) where n.name=~'.*(?i){0}.*'  return n")
    @Query("match (n:House{defaultLocale:true}) where n.name=~{0}  return n")
    List<House> getHousesByPattern(String pattern);
}
