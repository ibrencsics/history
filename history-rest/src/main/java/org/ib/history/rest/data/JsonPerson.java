package org.ib.history.rest.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonPerson extends JsonBase {

    private String dateOfBirth;
    private String dateOfDeath;

    private JsonPerson father;
    private JsonPerson mother;

    private List<JsonHouse> houses;


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

    public List<JsonHouse> getHouses() {
        return houses;
    }

    public void setHouses(List<JsonHouse> houses) {
        this.houses = houses;
    }
}
