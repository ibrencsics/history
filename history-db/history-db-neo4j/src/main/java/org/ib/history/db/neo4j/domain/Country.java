package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Country extends AbstractEntity {

    private String name;

    public Country(String name) {
        this.name = name;
    }

    public Country() {
    }
}
