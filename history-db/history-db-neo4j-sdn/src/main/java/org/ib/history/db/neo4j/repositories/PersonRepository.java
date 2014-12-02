package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Country;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Rules;
import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

public interface PersonRepository extends GraphRepository<Person>, PersonRepositoryCustom {

    @Query("match (n:Person{defaultLocale:true}) return n order by n.name")
    List<Person> getPersons();

    @Query("match (n:Person{defaultLocale:true}) return n order by n.dateOfBirth desc skip {0} limit {1}")
    List<Person> getPersons(int start, int length);

    @Query("match (p:Person{defaultLocale:true, name:{0}}) return p")
    List<Person> getPersonsByName(String name);

    @Query("match (n:Person{defaultLocale:true}) where n.name=~{0} return n")
    List<Person> getPersonsByPattern(String pattern);

    @Query("match (p:Person{defaultLocale:true})-[r:RULES]->(c:Country) where c.name={0} return p")
    List<Person> getPersons(String countryName);

    @Query("match (p:Person{defaultLocale:true})-[r:RULES]->(c:Country) where c.name={0} return p,r,c order by r.fromDate")
    List<Ruler> getRulers(String countryName);

    @QueryResult
    public interface Ruler {
        @ResultColumn("p")
        Person person();

        @ResultColumn("r")
        Rules rules();

        @ResultColumn("c")
        Country country();
    }
}
