package org.ib.history.wiki.domain;

import org.ib.history.commons.data.FlexibleDate;

import java.util.ArrayList;
import java.util.List;

public class Royalty {

    private String articleName;
    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;
    private WikiNamedResource father;
    private WikiNamedResource mother;
    private List<WikiNamedResource> spouses = new ArrayList<>(1);
    private List<WikiNamedResource> issues = new ArrayList<>(5);
    private List<WikiNamedResource> houses = new ArrayList<>(1);
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

    public List<Succession> getSuccessions() {
        return successions;
    }

    public List<WikiNamedResource> getSpouses() {
        return spouses;
    }

    public List<WikiNamedResource> getIssues() { return issues; }

    public List<WikiNamedResource> getHouses() {
        return houses;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getSuccessions().stream().forEach((s) -> sb.append("\n\t" + s));

        return "Royalty{" +
                "articleName='" + articleName + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", dateOfDeath=" + dateOfDeath +
                ", \n\tfather=" + father +
                ", mother=" + mother +
                ", \n\tspouses=" + spouses +
                ", \n\tissues=" + issues +
                ", \n\thouses=" + houses +
                ", " + sb.toString() +
                '}';
    }

    public static class Succession {
        private List<WikiNamedResource> successionLinks = new ArrayList<>(3);
        private String successionRaw;
        private String successionNoLinks;
        private String successionNoLinksNoSmall;
        private FlexibleDate from;
        private FlexibleDate to;
        private WikiNamedResource predecessor;
        private WikiNamedResource successor;

        public List<WikiNamedResource> getSuccessionLinks() {
            return successionLinks;
        }

        public void setSuccessionLinks(List<WikiNamedResource> successionLinks) {
            this.successionLinks = successionLinks;
        }

        public String getSuccessionRaw() {
            return successionRaw;
        }

        public void setSuccessionRaw(String successionRaw) {
            this.successionRaw = successionRaw;
        }

        public String getSuccessionNoLinks() {
            return successionNoLinks;
        }

        public void setSuccessionNoLinks(String successionNoLinks) {
            this.successionNoLinks = successionNoLinks;
        }

        public String getSuccessionNoLinksNoSmall() {
            return successionNoLinksNoSmall;
        }

        public void setSuccessionNoLinksNoSmall(String successionNoLinksNoSmall) {
            this.successionNoLinksNoSmall = successionNoLinksNoSmall;
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

        public WikiNamedResource getPredecessor() {
            return predecessor;
        }

        public void setPredecessor(WikiNamedResource predecessor) {
            this.predecessor = predecessor;
        }

        public WikiNamedResource getSuccessor() {
            return successor;
        }

        public void setSuccessor(WikiNamedResource successor) {
            this.successor = successor;
        }

        @Override
        public String toString() {
            return "Succession{" +
                    "successionLinks=" + successionLinks +
                    ", from=" + from +
                    ", to=" + to +
                    ", predecessor=" + predecessor +
                    ", successor=" + successor +
                    '}';
        }
    }
}
