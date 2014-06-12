package org.ib.history.commons.data;

import org.ib.history.commons.utils.FullDateWrapper;

public class PersonDto extends AbstractDto {

    private FullDateWrapper dateOfBirth;
    private FullDateWrapper dateOfDeath;
    private HouseDto house;

    public FullDateWrapper getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(FullDateWrapper dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public FullDateWrapper getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(FullDateWrapper dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public HouseDto getHouse() {
        return house;
    }

    public void setHouse(HouseDto house) {
        this.house = house;
    }


    public PersonDto withId(Long id) {
        this.setId(id);
        return this;
    }

    public PersonDto withName(String name) {
        this.setName(name);
        return this;
    }

    public PersonDto withDateOfBirth(FullDateWrapper dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public PersonDto withDateOfDeath(FullDateWrapper dateOfDeath) {
        this.setDateOfDeath(dateOfDeath);
        return this;
    }

    public PersonDto withHouse(HouseDto house) {
        this.setHouse(house);
        return this;
    }

    @Override
    public String toString() {
        return "PersonDto id:" + getId() + " name:" + getName() +
                " born:" + getDateOfBirth().toString() + " death:" + getDateOfDeath().toString() +
                " house: " + getHouse().getName();
    }
}
