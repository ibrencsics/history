package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Ruler;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RulerRepository extends GraphRepository<Ruler>, RulerRepositoryCustom {
}
