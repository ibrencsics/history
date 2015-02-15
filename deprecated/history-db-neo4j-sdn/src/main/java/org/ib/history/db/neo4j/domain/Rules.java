package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;


@RelationshipEntity
public class Rules extends BaseEntity {

    @StartNode
    private Person ruler;

    @Fetch
    @EndNode
    private Country country;

    private String title;
    private Integer number;
    private String fromDate;
    private String toDate;


    public Rules() {
    }

    public Rules(Long id, Person ruler, Country country, String title, Integer number, String fromDate, String toDate) {
        setId(id);
        this.ruler = ruler;
        this.country = country;
        this.title = title;
        this.number = number;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Person getRuler() {
        return ruler;
    }


    public String getTitle() {
        return title;
    }

    public Integer getNumber() {
        return number;
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
