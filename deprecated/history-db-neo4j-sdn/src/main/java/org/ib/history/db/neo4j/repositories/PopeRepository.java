package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Pope;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface PopeRepository extends GraphRepository<Pope> {

    @Query("match (n:Pope{defaultLocale:true}) return n")
    List<Pope> getPopes();

    @Query("match (n:Pope{defaultLocale:true}) return n order by n.name skip {0} limit {1}")
    List<Pope> getPopes(int start, int length);

    @Query("match (n:Pope{defaultLocale:true}) where n.name=~{0}  return n")
    List<Pope> getPopesByPattern(String pattern);
}
