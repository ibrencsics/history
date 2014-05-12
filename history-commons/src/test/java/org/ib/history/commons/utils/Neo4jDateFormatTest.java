package org.ib.history.commons.utils;

import static junit.framework.Assert.*;
import org.junit.Test;

public class Neo4jDateFormatTest {

    @Test
    public void testParse() {
        Neo4jDateFormat neo4jDateFormat = new Neo4jDateFormat();
        DateWrapper dateWrapper;

        dateWrapper = neo4jDateFormat.parse("19950000");
        assertEquals(dateWrapper.asString(), "1995");

        dateWrapper = neo4jDateFormat.parse("19950600");
        assertEquals(dateWrapper.asString(), "1995-06");

        dateWrapper = neo4jDateFormat.parse("19950602");
        assertEquals(dateWrapper.asString(), "1995-06-02");

        dateWrapper = neo4jDateFormat.parse("-02350000");
        assertEquals(dateWrapper.asString(), "-235");

//        dateWrapper = neo4jDateFormat.parse("00000000");
//        System.out.println(dateWrapper.asString());

        dateWrapper = neo4jDateFormat.parse("10000101");
        assertEquals(dateWrapper.asString(), "1000-01-01");
    }
}
