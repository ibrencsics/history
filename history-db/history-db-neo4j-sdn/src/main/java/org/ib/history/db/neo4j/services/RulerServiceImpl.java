package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Ruler;
import org.ib.history.db.neo4j.repositories.PersonRepository;
import org.ib.history.db.neo4j.repositories.RulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RulerServiceImpl implements RulerService {

    @Autowired
    PersonRepository personRepo;

    @Autowired
    RulerRepository rulerRepo;

    @Override
    public RulerData addRuler(RulerData rulerData) {

//        Person person = DataTransformer.transform(personData);
        Person person = DataTransformer.transform(rulerData.getPerson());

        Ruler ruler = DataTransformer.transform(rulerData);
        for (Ruler.Translation<Ruler> translation : ruler.getLocales()) {
            rulerRepo.save(translation.getTranslation());
        }

        // TODO: add the RULES values separarely like the locales

        Ruler rulerCreated = rulerRepo.save(ruler);
        rulerRepo.addRuler(person, rulerCreated);
        return DataTransformer.transform(rulerCreated);
    }



    @Override
    public Set<RulerData> getRulers(PersonData personData) {
        Person person = null;
        if (personData.getId() != null) {
            person = personRepo.findOne(personData.getId());
        } else {
            throw new IllegalArgumentException("PersonData contains no id");
        }

        Set<RulerData> rulers = new HashSet<>();

        for (Ruler job : person.getJobs()) {
            Ruler jobFull = rulerRepo.findOne(job.getId());
            rulers.add(DataTransformer.transform(jobFull));
        }

        return rulers;
    }

    @Override
    public Set<RulerData> getAllRulers() {
        Set<RulerData> rulerDataList = new HashSet<>();

        Set<Ruler> rulerSet = rulerRepo.getAllRulers();
        for (Ruler ruler : rulerSet) {
            rulerDataList.add(DataTransformer.transform(ruler));
        }

        return rulerDataList;
    }

    @Override
    public void deleteRuler(RulerData rulerData) {
        rulerRepo.delete(rulerData.getId());
    }
}
