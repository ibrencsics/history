package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.HouseData;

import java.util.List;

public interface HouseService {
    List<HouseData> getHouses();
    List<HouseData> getHouses(int start, int length);
    List<HouseData> getHousesByPattern(String pattern);
    List<HouseData> getHousesById(List<HouseData> houseOnlyIds);
    HouseData addHouse(HouseData houseData);
    void deleteHouse(HouseData houseData);
}
