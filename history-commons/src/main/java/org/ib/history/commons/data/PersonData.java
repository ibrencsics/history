package org.ib.history.commons.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonData extends AbstractData<PersonData> {

//    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;
    private List<PersonData> parents;
    private HouseData house;
    private Set<RulerData> rulers;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

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
        if (parents==null) {
            parents = new ArrayList<PersonData>();
        }
        return parents;
    }

    public void setParents(List<PersonData> parents) {
        this.parents = parents;
    }

    public HouseData getHouse() {
        return house;
    }

    public void setHouse(HouseData house) {
        this.house = house;
    }

    public Set<RulerData> getRulers() {
        if (rulers==null) {
            rulers = new HashSet<RulerData>();
        }
        return rulers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("PersonData{ id=" + getId() + " name=" + getName());
        if (getDateOfBirth()!=null)
            sb.append(" birth=" + getDateOfBirth().toString());
        if (getDateOfDeath()!=null)
            sb.append(" death=" + getDateOfDeath().toString());
        if (getHouse()!=null)
            sb.append(" house=" + getHouse().getName());
        sb.append(" }");

        for (String locale : getLocales().keySet()) {
            PersonData localePerson = getLocales().get(locale);
            sb.append("\n\tid=" + localePerson.getId() + " name=" + localePerson.getName() + " lang=" + locale);
        }
        for (PersonData parent : getParents()) {
            sb.append("\n\tparent id=" + parent.getId() + " name=" + parent.getName());
        }

        return sb.toString();
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
            personData.setHouse(house);
            return this;
        }

        public Builder ruler(RulerData ruler) {
            personData.getRulers().add(ruler);
            return this;
        }

        public PersonData build() {
            return personData;
        }
    }
}
