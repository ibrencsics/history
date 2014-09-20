package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Country extends BaseEntityWithTranslation<Country> {

    private String name;

    @Fetch
    @RelatedToVia
    private Set<Translation<Country>> locales = new HashSet<>();

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Translation<Country>> getLocales() {
        return locales;
    }

    public void setLocales(Set<Translation<Country>> locales) {
        this.locales = locales;
    }
}
