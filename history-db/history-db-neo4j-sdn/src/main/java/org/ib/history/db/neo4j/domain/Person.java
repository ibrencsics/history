package org.ib.history.db.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person extends AbstractEntity<Person> {

    private String name;
    private String dateOfBirth;
    private String dateOfDeath;

    @RelatedTo(type = "CHILD_OF")
    private Set<Person> parents = new HashSet<Person>();

    @Fetch
    @RelatedTo(type = "IN_HOUSE")
    private House house;

    @RelatedTo(type = "AS", direction = Direction.OUTGOING)
    private Set<Ruler> jobs = new HashSet<>();

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

    public void saveJob(Neo4jOperations template, Ruler ruler) {
        addJob(ruler);
        template.save(this);
    }

    public void addJob(Ruler ruler) {
        this.jobs.add(ruler);
    }

    public void addParent(Person person) {
        this.parents.add(person);
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

    public Set<Ruler> getJobs() {
        return Collections.unmodifiableSet(this.jobs);
    }

    public Set<Person> getParents() {
        return Collections.unmodifiableSet(this.parents);
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
