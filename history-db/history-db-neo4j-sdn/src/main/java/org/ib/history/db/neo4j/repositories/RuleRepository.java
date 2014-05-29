package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Rule;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface RuleRepository extends GraphRepository<Rule> {
}
