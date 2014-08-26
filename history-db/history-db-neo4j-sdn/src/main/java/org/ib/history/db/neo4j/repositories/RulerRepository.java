package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Ruler;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Set;

public interface RulerRepository extends GraphRepository<Ruler>, RulerRepositoryCustom {

    @Query("match (n:Ruler{defaultLocale:true}) return n")
    Set<Ruler> getAllRulers();

//    @Query("match (n:Ruler{defaultLocale:true})<-[:AS]-(p:PERSON{defaultLocale:true, }) return n")
//    Set<Ruler> getRulerByPerson(Long personId);
}
