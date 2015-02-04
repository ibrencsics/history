package org.ib.history.db.neo4j;

import org.ib.history.db.neo4j.data.NeoPerson;
import org.ib.history.wiki.domain.WikiPerson;
import org.neo4j.graphdb.Label;

import java.util.Optional;

public interface NeoService {
    long save(WikiPerson wikiPerson);
    int countOf(Label label);
    @Deprecated
    Optional<WikiPerson> getPerson(String wikiPage);
    Optional<NeoPerson> getNeoPerson(String wikiPage);
}
