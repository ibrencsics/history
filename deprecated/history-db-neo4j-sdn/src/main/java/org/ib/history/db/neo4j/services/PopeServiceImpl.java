package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.PopeData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.Pope;
import org.ib.history.db.neo4j.repositories.PopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PopeServiceImpl implements PopeService {

    @Autowired
    PopeRepository popeRepo;

    @Override
    public List<PopeData> getPopes() {
        return getPopes(popeRepo.getPopes());
    }

    @Override
    public List<PopeData> getPopes(int start, int length) {
        return getPopes(popeRepo.getPopes(start, length));
    }

    private List<PopeData> getPopes(List<Pope> popeList) {
        List<PopeData> popeDataList = new ArrayList<>();

        for (Pope pope : popeList) {
            popeDataList.add(DataTransformer.transform(pope));
        }

        return popeDataList;
    }

    @Override
    public List<PopeData> getPopesByPattern(String pattern) {
        List<PopeData> popeDataList = new ArrayList<>();

        List<Pope> popeList = popeRepo.getPopesByPattern("(?i).*" + pattern + ".*");
        for (Pope pope : popeList) {
            popeDataList.add(DataTransformer.transform(pope));
        }

        return popeDataList;
    }

    @Override
    public PopeData getPopeById(PopeData popeOnlyId) {
        return DataTransformer.transform( popeRepo.findOne(popeOnlyId.getId()) );
    }

    @Override
    public PopeData addPope(PopeData popeData) {
        Pope pope = DataTransformer.transform(popeData);
        for (Pope.Translation<Pope> translation : pope.getLocales()) {
            popeRepo.save(translation.getTranslation());
        }

        Pope popeCreated = popeRepo.save(pope);
        return DataTransformer.transform(popeCreated);
    }

    @Override
    public void deletePope(PopeData popeData) {
        popeRepo.delete(popeData.getId());
    }
}
