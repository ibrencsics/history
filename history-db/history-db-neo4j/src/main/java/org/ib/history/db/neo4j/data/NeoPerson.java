package org.ib.history.db.neo4j.data;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.utils.Neo4jDateFormat;
import org.ib.history.db.neo4j.GenderType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NeoPerson extends NeoBaseData {

    private Optional<FlexibleDate> dateOfBirth = Optional.empty();
    private Optional<FlexibleDate> dateOfDeath = Optional.empty();
    private Optional<GenderType> gender = Optional.empty();

    private Optional<NeoPerson> father = Optional.empty();
    private Optional<NeoPerson> mother = Optional.empty();

    private List<NeoPerson> spouses = new ArrayList<>(1);
    private List<NeoPerson> issues = new ArrayList<>(5);
    private List<NeoHouse> houses = new ArrayList<>(1);

    private List<NeoSuccession> successions = new ArrayList<>(3);


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


    public List<NeoPerson> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    public void addIssue(NeoPerson issue) {
        this.issues.add(issue);
    }

    public List<NeoPerson> getSpouses() {
        return Collections.unmodifiableList(spouses);
    }

    public void addSpouse(NeoPerson spouse) {
        this.spouses.add(spouse);
    }

    public List<NeoHouse> getHouses() {
        return Collections.unmodifiableList(houses);
    }

    public void addHouse(NeoHouse house) {
        this.houses.add(house);
    }


    public List<NeoSuccession> getSuccessions() {
        return Collections.unmodifiableList(successions);
    }

    public void setSuccessions(List<NeoSuccession> successions) {
        this.successions = successions;
    }

    public void addSuccession(NeoSuccession succession) {
        this.successions.add(succession);
    }


    @Override
    public String toString() {
        return "\nNeoPerson{" +
                "wikiPage='" + getWikiPage() + '\'' +
                ", name='" + getName() + '\'' +
                ", dateOfBirth=" + (dateOfBirth.isPresent() ? Neo4jDateFormat.dateWrapperToString(dateOfBirth.get()) : "") +
                ", dateOfDeath=" + (dateOfDeath.isPresent() ? Neo4jDateFormat.dateWrapperToString(dateOfDeath.get()) : "")  +
                ", gender=" + gender +
                "\n, father=" + (father.isPresent() ? father.get().toBaseString() : "") +
                "\n, mother=" + (mother.isPresent() ? mother.get().toBaseString() : "") +
                "\n, spouses=" + spouses.stream().map(spouse -> spouse.toBaseString()).collect(Collectors.joining(", ")) +
                "\n, issues=" + issues.stream().map(issue -> issue.toBaseString()).collect(Collectors.joining(", ")) +
                "\n, houses=" + houses.stream().map(house -> house.toString()).collect(Collectors.joining(", ")) +
                "\n, successions=" + successions.stream().map(s -> s.toString()).collect(Collectors.joining(", ")) +
                '}';
    }

    public String toBaseString() {
        return "NeoPerson{" +
                "wikiPage='" + getWikiPage() + '\'' +
                ", name='" + getName() + '\'' +
                ", dateOfBirth=" + (dateOfBirth.isPresent() ? Neo4jDateFormat.dateWrapperToString(dateOfBirth.get()) : "") +
                ", dateOfDeath=" + (dateOfDeath.isPresent() ? Neo4jDateFormat.dateWrapperToString(dateOfDeath.get()) : "")  +
                ", gender=" + gender;
    }

    public String toWikiNameResourseString() {
        return "NeoPerson{" +
                "wikiPage='" + getWikiPage() + '\'' +
                ", name='" + getName() + '\'';
    }
}
