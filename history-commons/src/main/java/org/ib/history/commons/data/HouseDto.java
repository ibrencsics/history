package org.ib.history.commons.data;

public class HouseDto extends AbstractDto {

    public HouseDto withId(Long id) {
        this.setId(id);
        return this;
    }

    public HouseDto withName(String name) {
        this.setName(name);
        return this;
    }

    @Override
    public String toString() {
        return "HouseDto id:" + getId() + " name:" + getName();
    }
}
