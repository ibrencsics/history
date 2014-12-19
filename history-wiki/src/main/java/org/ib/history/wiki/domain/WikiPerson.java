package org.ib.history.wiki.domain;

import org.ib.history.commons.data.FlexibleDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WikiPerson {

    private WikiResource wikiPage;
    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;

    private WikiResource father;
    private WikiResource mother;
    private List<WikiPerson> spouses = new ArrayList<>(1);

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

        public Builder father(WikiResource father) {
            wikiPerson.setFather(father);
            return this;
        }

        public Builder mother(WikiResource mother) {
            wikiPerson.setMother(mother);
            return this;
        }

        public Builder spouse(WikiPerson spouse) {
            wikiPerson.addSpouse(spouse);
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

    public WikiResource getFather() {
        return father;
    }

    public void setFather(WikiResource father) {
        this.father = father;
    }

    public WikiResource getMother() {
        return mother;
    }

    public void setMother(WikiResource mother) {
        this.mother = mother;
    }

    public void addSpouse(WikiPerson spouse) {
        this.spouses.add(spouse);
    }

    public List<WikiPerson> getSpouses() {
        return Collections.unmodifiableList(spouses);
    }

    @Override
    public String toString() {
        return "WikiPerson{" +
                "wikiPage='" + wikiPage + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", father=" + father +
                ", mother=" + mother +
                ", spouses=" + spouses +
                '}';
    }
}
