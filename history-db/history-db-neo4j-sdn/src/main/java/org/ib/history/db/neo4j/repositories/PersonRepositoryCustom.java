package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;

public interface PersonRepositoryCustom {
    void changeName(Long id, String name);
}
