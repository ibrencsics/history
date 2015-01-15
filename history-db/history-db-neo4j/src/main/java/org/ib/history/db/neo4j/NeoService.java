package org.ib.history.db.neo4j;

import org.ib.history.wiki.domain.WikiPerson;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;

public interface NeoService {
    long save(WikiPerson wikiPerson);
    int getPersonsCount();
}
