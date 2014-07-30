package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.HouseData;
import org.ib.history.db.neo4j.domain.DataTransformer;
import org.ib.history.db.neo4j.domain.House;
import org.ib.history.db.neo4j.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    @Autowired
    HouseRepository houseRepo;

    @Override
    public List<HouseData> getHouses() {
        List<HouseData> houseDataList = new ArrayList<>();

        List<House> houseList = houseRepo.getAllHouses();
        for (House house : houseList) {
            houseDataList.add(DataTransformer.transform(house));
        }

        return houseDataList;
    }

    @Override
    public HouseData addHouse(HouseData houseData) {
        House house = DataTransformer.transform(houseData);
        for (House.Translation<House> translation : house.getLocales()) {
            houseRepo.save(translation.getTranslation());
        }

        House houseCreated = houseRepo.save(house);
        return DataTransformer.transform(houseCreated);
    }

    @Override
    public void deleteHouse(HouseData houseData) {
        houseRepo.delete(houseData.getId());
    }
}
