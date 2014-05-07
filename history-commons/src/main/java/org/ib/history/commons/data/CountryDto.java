package org.ib.history.commons.data;

public class CountryDto extends AbstractDto {

    public CountryDto withId(Long id) {
        this.setId(id);
        return this;
    }

    public CountryDto withName(String name) {
        this.setName(name);
        return this;
    }

    @Override
    public String toString() {
        return "CountryDto id:" + getId() + " name:" + getName();
    }
}
