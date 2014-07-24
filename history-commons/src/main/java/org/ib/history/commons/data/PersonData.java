package org.ib.history.commons.data;

import java.util.ArrayList;
import java.util.List;

public class PersonData extends AbstractData<PersonData> {

//    private String name;
    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;
    private List<PersonData> children;

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

    public List<PersonData> getChildren() {
        if (children==null) {
            children = new ArrayList<PersonData>();
        }
        return children;
    }

    public void setChildren(List<PersonData> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("PersonData{ id=" + getId() + " name=" + getName());
        if (getDateOfBirth()!=null)
            sb.append(" birth=" + getDateOfBirth().toString());
        if (getDateOfDeath()!=null)
            sb.append(" death=" + getDateOfDeath().toString());
        sb.append(" }");

        for (String locale : getLocales().keySet()) {
            PersonData localePerson = getLocales().get(locale);
            sb.append("\n\tid=" + localePerson.getId() + " name=" + localePerson.getName() + " lang=" + locale);
        }
        for (PersonData child : getChildren()) {
            sb.append("\n\tchild id=" + child.getId() + " name=" + child.getName());
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

        public Builder child(PersonData child) {
            personData.getChildren().add(child);
            return this;
        }

        public Builder locale(String locale, PersonData localePersonData) {
            personData.addLocale(locale, localePersonData);
            return this;
        }

        public PersonData build() {
            return personData;
        }
    }
}
