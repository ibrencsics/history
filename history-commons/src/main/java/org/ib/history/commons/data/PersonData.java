package org.ib.history.commons.data;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonData extends AbstractData<PersonData> {

    private String gender;
    private String alias;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;

    private List<PersonData> parents = new ArrayList<>();
    private Set<HouseData> houses = new HashSet<>();
    private Set<SpouseData> spouses = new HashSet<>();

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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


    public List<PersonData> getParents() {
        return parents;
    }

    public void setParents(List<PersonData> parents) {
        this.parents = parents;
    }

    public Set<HouseData> getHouses() {
        return houses;
    }

    public void setHouses(Set<HouseData> houses) {
        this.houses = houses;
    }

    public Set<SpouseData> getSpouses() {
        return spouses;
    }

    public void setSpouses(Set<SpouseData> spouses) {
        this.spouses = spouses;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("PersonData{ id=" + getId() + " name=" + getName());
        sb.append(" gender=" + getGender());
        sb.append(" alias=" + getAlias());
        if (getDateOfBirth()!=null)
            sb.append(" birth=" + getDateOfBirth().toString());
        if (getDateOfDeath()!=null)
            sb.append(" death=" + getDateOfDeath().toString());
        sb.append(" }");

        for (String locale : getLocales().keySet()) {
            PersonData localePerson = getLocales().get(locale);
            sb.append("\n\tid=" + localePerson.getId() + " name=" + localePerson.getName() + " lang=" + locale);
        }
        for (PersonData parent : getParents()) {
            sb.append("\n\tparent id=" + parent.getId() + " name=" + parent.getName());
        }

        for (HouseData house : getHouses()) {
            sb.append("\n\thouse id=" + house.getId() + " name=" + house.getName());
        }

        for (SpouseData spouse : getSpouses()) {
            PersonData other= (spouse.getPerson1().equals(this) ? spouse.getPerson2() : spouse.getPerson1());
            sb.append("\r\nspouse id=" + other.getId() + " name=" + other.getName());
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==null) return false;

        if (!(obj instanceof PersonData)) return false;

        PersonData that = (PersonData)obj;

        if (this==that) return true;

        if (this.getId() == that.getId()) return true;
        if (this.getName().equals(that.getName())) return true;

        return false;
    }

    public static class Builder{
        PersonData personData = new PersonData();

        public Builder id(Long id) {
            personData.setId(id);
            return this;
        }

        public Builder name(String name) {
            personData.setName(name);
            return this;
        }

        public Builder gender(String gender) {
            personData.setGender(gender);
            return this;
        }

        public Builder alias(String alias) {
            personData.setAlias(alias);
            return this;
        }

        public Builder dateOfBirth(FlexibleDate dateOfBirth) {
            personData.setDateOfBirth(dateOfBirth);
            return this;
        }

        public Builder dateOfDeath(FlexibleDate dateOfDeath) {
            personData.setDateOfDeath(dateOfDeath);
            return this;
        }

        public Builder parent(PersonData parent) {
            personData.getParents().add(parent);
            return this;
        }

        public Builder locale(String locale, PersonData localePersonData) {
            personData.addLocale(locale, localePersonData);
            return this;
        }

        public Builder house(HouseData house) {
            personData.getHouses().add(house);
            return this;
        }

        public PersonData build() {
            return personData;
        }
    }
}
