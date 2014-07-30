package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class House extends AbstractEntity<House> {

    private String name;

    @Fetch
    @RelatedToVia
    private Set<Translation<House>> locales;

    public House() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Translation<House>> getLocales() {
        if (locales==null) {
            locales = new HashSet<>();
        }
        return locales;
    }

    public void setLocales(Set<Translation<House>> locales) {
        this.locales = locales;
    }
}
