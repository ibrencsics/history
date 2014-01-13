package org.ib.history.eval.db.domain;

import javax.persistence.Entity;

import org.springframework.util.Assert;

@Entity
public class Country extends AbstractEntity {

    private String name;

    public Country(String name) {
        Assert.hasText(name, "Country name must not be null or empty!");

        this.name = name;
    }

    public Country() {}

    public Country getCopy() {
        return new Country(this.name);
    }

    public String getName() {
        return this.name;
    }
}
