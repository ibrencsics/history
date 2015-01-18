package org.ib.history.db.neo4j;

import org.neo4j.graphdb.RelationshipType;

public enum WikiRelationships implements RelationshipType {
    HAS_FATHER, HAS_MOTHER, IS_SPOUSE_OF, HAS_ISSUE, IN_HOUSE
}
