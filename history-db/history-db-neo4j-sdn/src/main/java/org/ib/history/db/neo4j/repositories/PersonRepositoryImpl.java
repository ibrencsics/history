package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    private Neo4jOperations template;

    @Override
    @Transactional
    public void changeName(Long id, String name) {
        Person personFound = template.findOne(id, Person.class);
        personFound.setName(name);
        template.save(personFound);
    }

    @Override
    public void deleteParents(Long id) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        template.query("match (n)-[c:CHILD_OF]-() where id(n)={nodeId} delete c", params);
    }

    @Override
    public void addParent(Long id, Long parentId) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        params.put("parentNodeId", parentId);
        template.query("match (n),(m) where id(n)={nodeId} and id(m)={parentNodeId} create (n)-[c:CHILD_OF]->(m)", params);
    }
}
