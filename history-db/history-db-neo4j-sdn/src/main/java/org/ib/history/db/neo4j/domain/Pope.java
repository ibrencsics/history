package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Pope extends BaseEntityWithTranslation<Pope> {

    private String name;
    private Integer number;
    private String fromDate;
    private String toDate;

    @Fetch
    @RelatedToVia
    private Set<Translation<Pope>> locales = new HashSet<>();

    public Pope() {
    }

    public Pope(Long id) {
        setId(id);
    }

    public Pope(Long id, String name) {
        setId(id);
        this.name = name;
    }

    public Pope(Long id, String name, Integer number, String fromDate, String toDate) {
        setId(id);
        this.name = name;
        this.number = number;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }


    public Set<Translation<Pope>> getLocales() {
        return locales;
    }

    public void setLocales(Set<Translation<Pope>> locales) {
        this.locales = locales;
    }
}
