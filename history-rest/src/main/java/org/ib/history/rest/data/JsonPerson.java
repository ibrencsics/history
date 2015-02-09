package org.ib.history.rest.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonPerson extends JsonBase {

    private String dateOfBirth;
    private String dateOfDeath;

    private JsonPerson father;
    private JsonPerson mother;

    private List<JsonPerson> spouses;
    private List<JsonPerson> issues;
    private List<JsonHouse> houses;
    private List<JsonJob> jobs;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public JsonPerson getFather() {
        return father;
    }

    public void setFather(JsonPerson father) {
        this.father = father;
    }

    public JsonPerson getMother() {
        return mother;
    }

    public void setMother(JsonPerson mother) {
        this.mother = mother;
    }

    public List<JsonPerson> getSpouses() {
        return spouses;
    }

    public void setSpouses(List<JsonPerson> spouses) {
        this.spouses = spouses;
    }

    public List<JsonPerson> getIssues() {
        return issues;
    }

    public void setIssues(List<JsonPerson> issues) {
        this.issues = issues;
    }

    public List<JsonHouse> getHouses() {
        return houses;
    }

    public void setHouses(List<JsonHouse> houses) {
        this.houses = houses;
    }

    public List<JsonJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<JsonJob> jobs) {
        this.jobs = jobs;
    }
}
