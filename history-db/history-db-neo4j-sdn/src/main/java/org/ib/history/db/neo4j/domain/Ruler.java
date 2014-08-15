package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Date;
import java.util.Set;

@NodeEntity
public class Ruler extends AbstractEntity<Ruler> {

    public static final String RULES = "RULES";

    @RelatedToVia(type = RULES)
    @Fetch
    Set<Rules> rules;

    private String name;
    private String alias;
    private String title;

    public Ruler() {
    }

    public Ruler(String name, String alias, String title) {
        this.name = name;
        this.alias = alias;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }
}
