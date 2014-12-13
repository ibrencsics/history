package org.ib.history.wiki.parser;

import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PageLink;

import java.util.ArrayList;
import java.util.List;

public class Royalty {

    private String articleName;
    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;
    private PageLink father;
    private PageLink mother;
    private List<Succession> successions = new ArrayList<>();

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

    public List<Succession> getSuccessions() {
        return successions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getSuccessions().stream().forEach((s) -> sb.append("\n" + s));

        return "Royalty{" +
                "articleName='" + articleName + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", \n\tfather=" + father +
                ", mother=" + mother +
                ", " + sb.toString() +
                '}';
    }

    public static class Succession {
        private List<PageLink> countries = new ArrayList<>(3);
        private FlexibleDate from;
        private FlexibleDate to;

        public List<PageLink> getCountries() {
            return countries;
        }

        public void setCountries(List<PageLink> countries) {
            this.countries = countries;
        }

        public FlexibleDate getFrom() {
            return from;
        }

        public void setFrom(FlexibleDate from) {
            this.from = from;
        }

        public FlexibleDate getTo() {
            return to;
        }

        public void setTo(FlexibleDate to) {
            this.to = to;
        }

        @Override
        public String toString() {
            return "Succession{" +
                    ", \n\tcountries=" + countries +
                    ", \n\tfrom=" + from +
                    ", to=" + to +
                    '}';
        }
    }
}
