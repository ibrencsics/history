package org.ib.history.commons.data;

public class CountryDto extends AbstractDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryDto withId(Long id) {
        this.setId(id);
        return this;
    }

    public CountryDto withName(String name) {
        this.setName(name);
        return this;
    }
}
