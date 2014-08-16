package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RelationshipEntity
public class Rules extends AbstractEntity {

    @StartNode
    private Ruler ruler;

    @Fetch
    @EndNode
    private Country country;

    private String fromDate;
    private String toDate;


    public Rules() {
    }

    public Rules(Long id, Ruler ruler, Country country, String fromDate, String toDate) {
        setId(id);
        this.ruler = ruler;
        this.country = country;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Ruler getRuler() {
        return ruler;
    }

    public Country getCountry() {
        return country;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }
}
