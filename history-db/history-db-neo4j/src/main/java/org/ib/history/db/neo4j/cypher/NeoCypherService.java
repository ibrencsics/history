package org.ib.history.db.neo4j.cypher;

import java.util.List;

public interface NeoCypherService {
    List<String> getPersonsByPattern(String pattern);
}
