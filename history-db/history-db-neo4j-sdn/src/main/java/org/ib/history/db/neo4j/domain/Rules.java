package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import java.util.Date;

@RelationshipEntity
public class Rules extends AbstractEntity {

    @StartNode
    private Ruler ruler;

    @Fetch
    @EndNode
    private Country country;

    private Date fromDate;
    private Date toDate;

    public Rules() {
    }

    public Rules(Ruler ruler, Country country, Date fromDate, Date toDate) {
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

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
}
