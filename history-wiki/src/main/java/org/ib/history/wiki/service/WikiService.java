package org.ib.history.wiki.service;

import org.ib.history.wiki.domain.WikiPerson;

public interface WikiService {
    WikiPerson getPerson(String wikiPage);
}
