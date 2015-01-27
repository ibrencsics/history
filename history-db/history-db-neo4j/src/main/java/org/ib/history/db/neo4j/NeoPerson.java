package org.ib.history.db.neo4j;

import org.ib.history.commons.data.FlexibleDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NeoPerson {

    private String wikiPage;
    private String name;

    private Optional<FlexibleDate> dateOfBirth;
    private Optional<FlexibleDate> dateOfDeath;
    private Optional<GenderType> gender;

    private Optional<NeoPerson> father;
    private Optional<NeoPerson> mother;

    private List<NeoPerson> spouses = new ArrayList<>(1);
    private List<NeoPerson> issues = new ArrayList<>(5);


    public String getWikiPage() {
        return wikiPage;
    }

    public void setWikiPage(String wikiPage) {
        this.wikiPage = wikiPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<FlexibleDate> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(FlexibleDate dateOfBirth) {
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
    }

    public Optional<FlexibleDate> getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(FlexibleDate dateOfDeath) {
        this.dateOfDeath = Optional.ofNullable(dateOfDeath);
    }

    public Optional<GenderType> getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = Optional.ofNullable(gender);
    }

    public Optional<NeoPerson> getFather() {
        return father;
    }

    public void setFather(Optional<NeoPerson> father) {
        this.father = father;
    }

    public Optional<NeoPerson> getMother() {
        return mother;
    }

    public void setMother(Optional<NeoPerson> mother) {
        this.mother = mother;
    }
}
