package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Rules;
import org.ib.history.db.neo4j.domain.Spouse;

public interface PersonRepositoryCustom {
    void changeName(Long id, String name);

    void addParent(Long id, Long parentId);
    void deleteParent(Long id, Long parentId);
    void deleteParents(Long id);

    void addHouse(Long id, Long houseId);
    void deleteHouses(Long id);

    void addSpouse(Spouse spouse);
    void deleteSpouses(Long id);

    void addRules(Rules rules);
    void deleteRules(Long id);

    void setPope(Long id, Long popeId);
    void deletePope(Long id);
}
