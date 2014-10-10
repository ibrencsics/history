package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;

import java.util.List;

public interface PersonRepositoryCustom {
    void changeName(Long id, String name);

    void addParent(Long id, Long parentId);
    void deleteParent(Long id, Long parentId);
    void deleteParents(Long id);
}
