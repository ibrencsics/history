package org.ib.history.db.neo4j.rest;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.db.neo4j.Neo4jService;
import org.ib.history.db.neo4j.Converter;
import org.neo4j.rest.graphdb.entity.RestNode;
import org.neo4j.rest.graphdb.util.QueryResult;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Neo4jRestService implements Neo4jService {

//    private static final Logger logger = LoggerFactory.getLogger(Neo4jRestService.class);
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Neo4jRestService.class);

    @Override
    public List<CountryDto> getCountries() {

        Neo4jRestTemplate template = new Neo4jRestTemplate();

        Converter<List<CountryDto>> converter = new Converter<List<CountryDto>>() {
            @Override
            public List<CountryDto> convert(QueryResult<Map<String, Object>> result) {

                List<CountryDto> countryList = new ArrayList<>();

                Iterator<Map<String, Object>> iter = result.iterator();
                while (iter.hasNext()) {
                    Map<String,Object> map = iter.next();

                    for (Map.Entry<String,Object> entry : map.entrySet()) {
                        System.out.println(entry.getKey() +  " : " + entry.getValue());

                        CountryDto county = new CountryDto();
                        countryList.add(county);

                        RestNode restNode = (RestNode) entry.getValue();
                        Iterable<String> props = restNode.getPropertyKeys();
                        Iterator<String> it = props.iterator();
                        while (it.hasNext()) {
                            String prop = it.next();
                            System.out.println("  " + prop + " : " + restNode.getProperty(prop));
                            if (prop=="name") county.setName(restNode.getProperty(prop).toString());
                        }
                    }
                }

                return countryList;
            }
        };

       LOG.info("asd");
       return template.query("match (c:Country) return c", converter);
    }
}
