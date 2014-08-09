package org.ib.history.db.neo4j.services;

import org.ib.history.commons.data.HouseData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
@Transactional
public class HouseServiceImplTest {

    @Autowired
    HouseService houseService;

    @Test
    public void testByPattern() {
        HouseData houseData = new HouseData.Builder().name("aaa").build();
        houseService.addHouse(houseData);

        houseData = new HouseData.Builder().name("bbb").build();
        houseService.addHouse(houseData);

        List<HouseData> houses = houseService.getHousesByPattern("A");
        System.out.println(houses);
    }
}
