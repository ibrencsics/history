package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Ruler;
import org.ib.history.db.neo4j.repositories.RulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class RulerServiceImpl implements RulerService {

    @Autowired
    RulerRepository rulerRepo;

    @Override
    public RulerData addRuler(PersonData personData, RulerData rulerData) {

        Person person = DataTransformer.transform(personData);

        Ruler ruler = DataTransformer.transform(rulerData);
        for (Ruler.Translation<Ruler> translation : ruler.getLocales()) {
            rulerRepo.save(translation.getTranslation());
        }

        Ruler rulerCreated = rulerRepo.save(ruler);
        rulerRepo.addRuler(person, rulerCreated);
        return DataTransformer.transform(rulerCreated);
    }

    @Override
    public Set<RulerData> getRulers(PersonData personData) {
        return null;
    }
}
