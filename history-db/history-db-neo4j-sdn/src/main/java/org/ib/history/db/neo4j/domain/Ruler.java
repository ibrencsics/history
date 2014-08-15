package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Date;
import java.util.Set;

public class Ruler extends Person {

    public static final String RULES = "RULES";

    @RelatedToVia(type = RULES)
    @Fetch
    Set<Rules> rules;

    private String name;
    private String alias;
    private String title;
}
