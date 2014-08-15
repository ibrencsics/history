package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;

import java.util.Set;

public interface RulerService {
    void addRuler(PersonData person, RulerData ruler);
    Set<RulerData> getRulers(PersonData person);
}
