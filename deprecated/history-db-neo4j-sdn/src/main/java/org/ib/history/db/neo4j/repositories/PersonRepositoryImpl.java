package org.ib.history.db.neo4j.repositories;

import org.ib.history.db.neo4j.domain.Person;
import org.ib.history.db.neo4j.domain.Pope;
import org.ib.history.db.neo4j.domain.Rules;
import org.ib.history.db.neo4j.domain.Spouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    private Neo4jOperations template;

    @Override
    @Transactional
    public void changeName(Long id, String name) {
        Person personFound = template.findOne(id, Person.class);
        personFound.setName(name);
        template.save(personFound);
    }

    @Override
    public void addParent(Long id, Long parentId) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        params.put("parentNodeId", parentId);
        template.query("match (n),(m) where id(n)={nodeId} and id(m)={parentNodeId} create (n)-[c:CHILD_OF]->(m)", params);
    }

    @Override
    public void deleteParent(Long id, Long parentId) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        params.put("parentNodeId", parentId);
        template.query("match (n)-[c:CHILD_OF]-(m) where id(n)={nodeId} and id(m)={parentNodeId} delete c", params);
    }

    @Override
    public void deleteParents(Long id) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        template.query("match (n)-[c:CHILD_OF]-() where id(n)={nodeId} delete c", params);
    }

    @Override
    public void addHouse(Long id, Long houseId) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        params.put("houseNodeId", houseId);
        template.query("match (n),(m) where id(n)={nodeId} and id(m)={houseNodeId} create (n)-[c:IN_HOUSE]->(m)", params);
    }

    @Override
    public void deleteHouses(Long id) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        template.query("match (n)-[c:IN_HOUSE]-() where id(n)={nodeId} delete c", params);
    }

    @Override
    public void addSpouse(Spouse spouse) {
        Map<String,Object> params = new HashMap<>();
        params.put("person1Id", spouse.getPerson1().getId());
        params.put("person2Id", spouse.getPerson2().getId());
        params.put("fromDate", spouse.getFromDate());
        params.put("toDate", spouse.getToDate());
        template.query("match (p1:Person), (p2:Person) where id(p1)={person1Id} and id(p2)={person2Id} " +
                "create (p1)-[:SPOUSE{ fromDate:{fromDate}, toDate:{toDate}, __type__:'org.ib.history.db.neo4j.domain.Spouse' }]->(p2)", params);
    }

    @Override
    public void deleteSpouses(Long id) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        template.query("match (n)-[c:SPOUSE]-() where id(n)={nodeId} delete c", params);
    }

    @Override
    public void addRules(Rules rules) {
        Map<String,Object> params = new HashMap<>();
        params.put("personId", rules.getRuler().getId());
        params.put("countryId", rules.getCountry().getId());
        params.put("fromDate", rules.getFromDate());
        params.put("toDate", rules.getToDate());
        params.put("title", rules.getTitle());
        params.put("number", rules.getNumber());
        template.query("match (p:Person), (c:Country) where id(p)={personId} and id(c)={countryId} " +
                "create (p)-[:RULES{ fromDate:{fromDate}, toDate:{toDate}, title:{title}, number:{number}, " +
                "__type__:'org.ib.history.db.neo4j.domain.Rules' }]->(c)", params);
    }

    @Override
    public void deleteRules(Long id) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        template.query("match (n)-[c:RULES]-() where id(n)={nodeId} delete c", params);
    }

    @Override
    public void setPope(Long id, Long popeId) {
        Map<String,Object> params = new HashMap<>();
        params.put("personId", id);
        params.put("popeId", popeId);
        template.query("match (n:Person), (m:Pope) where id(n)={personId} and id(m)={popeId} create (n)-[c:IS_POPE]->(m)", params);
    }

    @Override
    public void deletePope(Long id) {
        Map<String,Object> params = new HashMap<>();
        params.put("nodeId", id);
        template.query("match (n)-[c:IS_POPE]-() where id(n)={nodeId} delete c", params);
    }


    @Override
    public void getRules(String countryName) {
        Map<String,Object> params = new HashMap<>();
        params.put("countryName", countryName);
        Result<Map<String,Object>> result = template.query("match (p:Person{defaultLocale:true})-[r:RULES]->(c:Country) where c.name={countryName} return p", params);
        result.single();
    }
}
