package org.ib.history.db.neo4j.cypher;

import org.ib.history.db.neo4j.data.NeoStatistics;

import java.util.List;

public interface NeoCypherService {
    List<String> getPersonsByPattern(String pattern);
    NeoStatistics getStatistics();
}
