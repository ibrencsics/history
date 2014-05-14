package org.ib.history.db.neo4j.java;

import org.neo4j.test.TestGraphDatabaseFactory;

public class Neo4jJavaService {

    Neo4jJavaTemplate template;

    public Neo4jJavaService() {
        this.template = new Neo4jJavaTemplate(new TestGraphDatabaseFactory().newImpermanentDatabase());
    }

    public Neo4jJavaService(Neo4jJavaTemplate template) {
        this.template = template;
    }
}
