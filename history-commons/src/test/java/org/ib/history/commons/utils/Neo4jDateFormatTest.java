package org.ib.history.commons.utils;

import org.junit.Test;

public class Neo4jDateFormatTest {

    @Test
    public void testParse() {
        Neo4jDateFormat neo4jDateFormat = new Neo4jDateFormat();
        DateWrapper dateWrapper;

        dateWrapper = neo4jDateFormat.parse("19950000");
        System.out.println(dateWrapper.asString());

        dateWrapper = neo4jDateFormat.parse("19950600");
        System.out.println(dateWrapper.asString());

        dateWrapper = neo4jDateFormat.parse("19950602");
        System.out.println(dateWrapper.asString());

        dateWrapper = neo4jDateFormat.parse("-02350000");
        System.out.println(dateWrapper.asString());

        dateWrapper = neo4jDateFormat.parse("00000000");
        System.out.println(dateWrapper.asString());

        dateWrapper = neo4jDateFormat.parse("10000101");
        System.out.println(dateWrapper.asString());
    }
}
