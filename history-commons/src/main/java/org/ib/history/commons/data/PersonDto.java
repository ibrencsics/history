package org.ib.history.commons.data;

import org.ib.history.commons.utils.DateWrapper;

import java.util.Date;

public class PersonDto extends AbstractDto {

    private DateWrapper dateOfBirth;
    private DateWrapper dateOfDeath;

    public DateWrapper getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateWrapper dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public DateWrapper getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(DateWrapper dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public PersonDto withId(Long id) {
        this.setId(id);
        return this;
    }

    public PersonDto withName(String name) {
        this.setName(name);
        return this;
    }

    public PersonDto withDateOfBirth(DateWrapper dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public PersonDto withDateOfDeath(DateWrapper dateOfDeath) {
        this.setDateOfDeath(dateOfDeath);
        return this;
    }

    @Override
    public String toString() {
        return "PersonDto id:" + getId() + " name:" + getName() + " born:" + getDateOfBirth().asString() + " death:" + getDateOfDeath().asString();
    }
}
