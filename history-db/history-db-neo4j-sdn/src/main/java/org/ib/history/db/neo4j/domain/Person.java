package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person extends AbstractEntity {

    private String name;
    private String dateOfBirth;
    private String dateOfDeath;

    @RelatedTo(type = "PARENT_OF")
//    @Fetch
    private Set<Person> children = new HashSet<Person>();

    @RelatedTo(type = "IN_HOUSE")
    private House house;

    @Fetch
    @RelatedToVia
    private Set<Translation<Person>> locales;

    public Person() {
    }

    public Person(Long id, String name) {
        this.setId(id);
        this.name = name;
    }

    public Person(Long id, String name, String dateOfBirth, String dateOfDeath, House house) {
        this.setId(id);
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.house = house;
    }

    public void addChild(Person person) {
        this.children.add(person);
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public House getHouse() {
        return house;
    }

    public Set<Person> getChildren() {
        return Collections.unmodifiableSet(this.children);
    }

    public Set<Translation<Person>> getLocales() {
        if (locales==null) {
            locales = new HashSet<>();
        }
        return locales;
    }

    public void setLocales(Set<Translation<Person>> locales) {
        this.locales = locales;
    }
}
