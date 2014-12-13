package org.ib.history.wiki.parser;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PageLink;

public class Royalty {

    private String articleName;
    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;
    private PageLink father;
    private PageLink mother;


    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
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

    public PageLink getFather() {
        return father;
    }

    public void setFather(PageLink father) {
        this.father = father;
    }

    public PageLink getMother() {
        return mother;
    }

    public void setMother(PageLink mother) {
        this.mother = mother;
    }

    @Override
    public String toString() {
        return "Royalty{" +
                "articleName='" + articleName + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", \n\tfather=" + father +
                ", mother=" + mother +
                '}';
    }
}
