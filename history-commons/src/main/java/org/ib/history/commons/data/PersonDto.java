package org.ib.history.commons.data;


public class PersonDto extends AbstractDto {

    private FlexibleDate dateOfBirth;
    private FlexibleDate dateOfDeath;
    private HouseDto house;

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

    public PersonDto withDateOfBirth(FlexibleDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public PersonDto withDateOfDeath(FlexibleDate dateOfDeath) {
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
