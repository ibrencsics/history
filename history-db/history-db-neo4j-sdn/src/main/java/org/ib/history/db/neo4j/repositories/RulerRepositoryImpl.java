package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Ruler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;

public class RulerRepositoryImpl implements RulerRepositoryCustom {

    @Autowired
    private Neo4jOperations template;

    @Override
    public void addRuler(Person person, Ruler ruler) {
        person.addJob(template, ruler);
    }
}
