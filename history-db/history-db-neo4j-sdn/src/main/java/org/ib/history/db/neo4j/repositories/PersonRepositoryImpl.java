package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.transaction.annotation.Transactional;

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
}
