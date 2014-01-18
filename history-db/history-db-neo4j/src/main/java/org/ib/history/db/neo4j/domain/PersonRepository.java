package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {

    Person findOne(Long id);

//    <P extends Person> P save(Person P);

    Person findByName(String name);
}
