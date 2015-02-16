package org.ib.history.commons.utils;

import static junit.framework.Assert.*;

import org.ib.history.commons.data.FlexibleDate;
import org.junit.Test;

public class Neo4jDateFormatTest {

    @Test
    public void testParse() {
        FlexibleDate dateWrapper;

        dateWrapper = Neo4jDateFormat.parse("19950000");
        assertEquals(Neo4jDateFormat.dateWrapperToString(dateWrapper), "1995");

        dateWrapper = Neo4jDateFormat.parse("19950600");
        assertEquals(Neo4jDateFormat.dateWrapperToString(dateWrapper), "1995-06");

        dateWrapper = Neo4jDateFormat.parse("19950602");
        assertEquals(Neo4jDateFormat.dateWrapperToString(dateWrapper), "1995-06-02");

        dateWrapper = Neo4jDateFormat.parse("-02350000");
        assertEquals(Neo4jDateFormat.dateWrapperToString(dateWrapper), "-235");

//        dateWrapper = neo4jDateFormat.parseFull("00000000");
//        System.out.println(dateWrapper.asString());

        dateWrapper = Neo4jDateFormat.parse("10000101");
        assertEquals(Neo4jDateFormat.dateWrapperToString(dateWrapper), "1000-01-01");
    }
}