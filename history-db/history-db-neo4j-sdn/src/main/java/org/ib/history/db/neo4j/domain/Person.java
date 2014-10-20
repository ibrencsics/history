package org.ib.history.db.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Person extends BaseEntityWithTranslation<Person> {

    private String name;
    private String gender;
    private String alias;
    private String dateOfBirth;
    private String dateOfDeath;

    @RelatedTo(type = "CHILD_OF", direction = Direction.OUTGOING)
    private Set<Person> parents = new HashSet<Person>();

    @RelatedToVia(type = "SPOUSE", direction = Direction.BOTH)
    private Set<Spouse> spouses = new HashSet<>();

    @RelatedTo(type = "IN_HOUSE", direction = Direction.OUTGOING)
    private Set<House> houses = new HashSet<>();

    @Fetch
    @RelatedToVia(type = "RULES", direction = Direction.OUTGOING)
    private Set<Rules> rules = new HashSet<>();

    @RelatedTo(type = "IS_POPE", direction = Direction.OUTGOING)
    private Pope pope;

    @Fetch
    @RelatedToVia
    private Set<Translation<Person>> locales = new HashSet<>();


    public Person() {
    }

    public Person(Long id) {
        this.setId(id);
    }

    public Person(Long id, String name, String alias) {
        this.setId(id);
        this.name = name;
        this.alias = alias;
    }

    public Person(Long id, String name, String gender, String alias, String dateOfBirth, String dateOfDeath) {
        this.setId(id);
        this.name = name;
        this.gender = gender;
        this.alias = alias;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }

//    public void saveJob(Neo4jOperations template, Ruler ruler) {
//        addJob(ruler);
//        template.save(this);
//    }


//    public void addJob(Ruler ruler) {
//        this.jobs.add(ruler);
//    }

    public void addParent(Person person) {
        this.parents.add(person);
    }

    public void addSpouse(Spouse spouse) {
        this.spouses.add(spouse);
    }

    public void addHouse(House house) {
        this.houses.add(house);
    }

    public void addRule(Rules rule) {
        this.rules.add(rule);
    }

    public void setPope(Pope pope) {
        this.pope = pope;
    }


    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAlias() {
        return alias;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Set<Person> getParents() {
        return parents;
//        return Collections.unmodifiableSet(this.parents);
    }

    public Set<Spouse> getSpouses() {
        return Collections.unmodifiableSet(this.spouses);
    }

    public Set<House> getHouses() {
        return Collections.unmodifiableSet(this.houses);
    }

    public Set<Rules> getRules() {
        return Collections.unmodifiableSet(this.rules);
    }

    public Pope getPope() {
        return pope;
    }


    public void cleanParents() {
        parents.clear();
    }

    public void cleanHouses() {
        houses.clear();
    }


    public Set<Translation<Person>> getLocales() {
        return locales;
    }

    public void setLocales(Set<Translation<Person>> locales) {
        this.locales = locales;
    }
}
