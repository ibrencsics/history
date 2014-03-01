package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person extends AbstractEntity {

    private String name;
    private Date birthDate;
    private Date deathDate;

    @RelatedTo(type = "PARENT_OF")
    @Fetch
    private Set<Person> children = new HashSet<Person>();

    public Person() {
    }

    public Person(String name, Date birthDate, Date deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public void addChild(Person person) {
        this.children.add(person);
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public Set<Person> getChildren() {
        return Collections.unmodifiableSet(this.children);
    }
}
