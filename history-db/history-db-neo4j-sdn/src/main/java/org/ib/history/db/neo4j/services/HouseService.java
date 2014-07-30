package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.HouseData;

import java.util.List;

public interface HouseService {
    List<HouseData> getHouses();
    HouseData addHouse(HouseData houseData);
    void deleteHouse(HouseData houseData);
}
