package org.ib.history.db.neo4j;

import org.ib.history.wiki.domain.WikiPerson;

public interface NeoService {
    long save(WikiPerson wikiPerson);
}
