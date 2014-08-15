package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Ruler;

public interface RulerRepositoryCustom {
    void addRuler(Person person, Ruler ruler);
}
