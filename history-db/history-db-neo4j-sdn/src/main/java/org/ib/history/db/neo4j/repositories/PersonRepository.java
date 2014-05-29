package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Rule;
import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface PersonRepository extends GraphRepository<Person> {

    Person findOne(Long id);

//    <P extends Person> P save(Person P);

    Person findByName(String name);


    @Query(" MATCH person-[rules:RULES]->country " +
           " RETURN person, rules, country")
    List<Ruler> getRulers();

    @MapResult
    interface Ruler {
        @ResultColumn("person") Person getPerson();
        @ResultColumn("rules")
        Rule getRule();
        @ResultColumn("country")
        Country getCountry();
    }
}
