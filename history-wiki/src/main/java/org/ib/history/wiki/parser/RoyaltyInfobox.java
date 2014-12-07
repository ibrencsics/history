package org.ib.history.wiki.parser;

import org.ib.history.commons.data.FlexibleDate;

import java.util.ArrayList;
import java.util.List;

public class RoyaltyInfobox implements Infobox {

    private String articleName;
    private String name;
    private List<Succession> successions = new ArrayList<>(3);
    private List<String> spouses;
    private List<String> houses;
    private String father;
    private String mother;
    private FlexibleDate birth;
    private String birthPlace;
    private FlexibleDate death;
    private String deathPlace;
    private String burialPlace;
    private String religion;

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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public FlexibleDate getBirth() {
        return birth;
    }

    public void setBirth(FlexibleDate birth) {
        this.birth = birth;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public FlexibleDate getDeath() {
        return death;
    }

    public void setDeath(FlexibleDate death) {
        this.death = death;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public String getBurialPlace() {
        return burialPlace;
    }

    public void setBurialPlace(String burialPlace) {
        this.burialPlace = burialPlace;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public List<Succession> getSuccessions() {
        return successions;
    }

    public List<String> getSpouses() {
        return spouses;
    }

    public List<String> getHouses() {
        return houses;
    }

    public static class Succession {
        private String title;
        private List<String> countries = new ArrayList<>(3);
        private FlexibleDate from;
        private FlexibleDate to;
        private FlexibleDate coronation;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
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

        public FlexibleDate getCoronation() {
            return coronation;
        }

        public void setCoronation(FlexibleDate coronation) {
            this.coronation = coronation;
        }
    }
}
