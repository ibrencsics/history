package org.ib.history.wiki.service;

import org.ib.history.wiki.domain.WikiPerson;

import java.util.Optional;

public interface WikiService {
    Optional<WikiPerson> getPerson(String wikiPage);
}
