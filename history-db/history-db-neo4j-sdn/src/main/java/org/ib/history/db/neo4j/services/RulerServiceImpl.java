package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RulerServiceImpl implements RulerService {

    @Override
    public void addRuler(PersonData person, RulerData ruler) {

    }

    @Override
    public Set<RulerData> getRulers(PersonData person) {
        return null;
    }
}
