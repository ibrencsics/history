package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;

import java.util.Set;

public interface RulerService {
    RulerData addRuler(RulerData rulerData);
    Set<RulerData> getRulers(PersonData personData);
    Set<RulerData> getAllRulers();
    void deleteRuler(RulerData rulerData);
}
