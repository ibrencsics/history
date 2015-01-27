package org.ib.history.db.neo4j;

import org.ib.history.wiki.domain.WikiPerson;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;

import java.util.Optional;

public interface NeoService {
    long save(WikiPerson wikiPerson);
    int countOf(Label label);
    Optional<WikiPerson> getPerson(String wikiPage);
    Optional<NeoPerson> getNeoPerson(String wikiPage);
}
