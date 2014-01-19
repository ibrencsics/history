package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import java.util.Date;

@RelationshipEntity(type = "RULES")
public class Rule extends AbstractEntity {

    @StartNode
    private Person person;

    @Fetch
    @EndNode
    private Country country;

    private String fromDate;
    private String toDate;

    public Rule() {
    }

    public Rule(Person person, Country country, String fromDate, String toDate) {
        this.person = person;
        this.country = country;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Person getPerson() {
        return person;
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
