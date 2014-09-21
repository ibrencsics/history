package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity
public class Spouse extends BaseEntity {

    @StartNode
    private Person person1;

    @Fetch
    @EndNode
    private Person person2;

    private String fromDate;
    private String toDate;

    public Spouse() {}

    public Spouse(Long id, Person person1, Person person2, String fromDate, String toDate) {
        this.setId(id);
        this.person1 = person1;
        this.person2 = person2;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Person getPerson1() {
        return person1;
    }

    public Person getPerson2() {
        return person2;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }
}
