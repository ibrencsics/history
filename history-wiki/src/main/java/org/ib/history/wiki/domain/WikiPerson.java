package org.ib.history.wiki.domain;

import org.ib.history.commons.data.FlexibleDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WikiPerson {

    private WikiResource wikiPage;
    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;

    private WikiNamedResource father;
    private WikiNamedResource mother;
    private List<WikiNamedResource> spouses = new ArrayList<>(1);
    private List<WikiNamedResource> issues = new ArrayList<>(5);
    private List<WikiNamedResource> houses = new ArrayList<>(1);

    private List<WikiSuccession> successions = new ArrayList<>(3);

    public static class Builder {
        private WikiPerson wikiPerson = new WikiPerson();

        public Builder wikiPage(WikiResource wikiPage) {
            wikiPerson.setWikiPage(wikiPage);
            return this;
        }

        public Builder name(String name) {
            wikiPerson.setName(name);
            return this;
        }

        public Builder dateOfBirth(FlexibleDate dateOfBirth) {
            wikiPerson.setDateOfBirth(dateOfBirth);
            return this;
        }

        public Builder dateOfDeath(FlexibleDate dateOfDeath) {
            wikiPerson.setDateOfDeath(dateOfDeath);
            return this;
        }

        public Builder father(WikiNamedResource father) {
            wikiPerson.setFather(father);
            return this;
        }

        public Builder mother(WikiNamedResource mother) {
            wikiPerson.setMother(mother);
            return this;
        }

        public Builder spouse(WikiNamedResource spouse) {
            wikiPerson.addSpouses(Arrays.asList(spouse));
            return this;
        }

        public Builder spouse(List<WikiNamedResource> spouses) {
            wikiPerson.addSpouses(spouses);
            return this;
        }

        public Builder issue(List<WikiNamedResource> issues) {
            wikiPerson.addIssues(issues);
            return this;
        }

        public Builder house(List<WikiNamedResource> houses) {
            wikiPerson.addHouses(houses);
            return this;
        }

        public Builder succession(WikiSuccession succession) {
            wikiPerson.successions.add(succession);
            return this;
        }

        public WikiPerson build() {
            return wikiPerson;
        }
    }


    public WikiResource getWikiPage() {
        return wikiPage;
    }

    public void setWikiPage(WikiResource wikiPage) {
        this.wikiPage = wikiPage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WikiNamedResource getWikiNamedResource() {
        return new WikiNamedResource(getWikiPage().getLocalPart(), getName());
    }

    public FlexibleDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(FlexibleDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public FlexibleDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(FlexibleDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public WikiNamedResource getFather() {
        return father;
    }

    public void setFather(WikiNamedResource father) {
        this.father = father;
    }

    public WikiNamedResource getMother() {
        return mother;
    }

    public void setMother(WikiNamedResource mother) {
        this.mother = mother;
    }

    public void addSpouses(List<WikiNamedResource> spouse) {
        this.spouses.addAll(spouse);
    }

    public List<WikiNamedResource> getSpouses() {
        return Collections.unmodifiableList(spouses);
    }

    public void addIssues(List<WikiNamedResource> issues) {
        this.issues.addAll(issues);
    }

    public List<WikiNamedResource> getIssues() {
        return Collections.unmodifiableList(issues);
    }

    public void addHouses(List<WikiNamedResource> houses) {
        this.houses.addAll(houses);
    }

    public List<WikiNamedResource> getHouses() {
        return Collections.unmodifiableList(houses);
    }

    public List<WikiSuccession> getSuccessions() {
        return Collections.unmodifiableList(successions);
    }

    @Override
    public String toString() {
        return "WikiPerson{" +
                "wikiPage='" + wikiPage + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", \n\tfather=" + father.getLocalPart() +
                ", mother=" + mother.getLocalPart() +
                ", \n\tspouses=" + spouses +
                ", \n\tissues=" + issues +
                ", \n\thouses=" + houses +
                ", \n\tsuccession=" + successions +
                '}';
    }
}
