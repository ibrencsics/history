package org.ib.history.db.neo4j.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Ruler extends AbstractEntity<Ruler> {

    public static final String RULES = "RULES";

    @RelatedToVia(type = RULES)
    @Fetch
    Set<Rules> allRules = new HashSet<>();

    private String name;
    private String alias;
    private String title;

    @Fetch
    @RelatedToVia
    private Set<Translation<Ruler>> locales;

    @Fetch
    @RelatedTo(type = "AS", direction = Direction.INCOMING)
    private Person person;

    public Ruler() {
    }

    public Ruler(Long id, String name, String alias, String title) {
        setId(id);
        this.name = name;
        this.alias = alias;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    public void addRules(Rules rules) {
        allRules.add(rules);
    }

    public Set<Rules> getAllRules() {
        return Collections.unmodifiableSet(allRules);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Translation<Ruler>> getLocales() {
        if (locales==null) {
            locales = new HashSet<>();
        }
        return locales;
    }
}
