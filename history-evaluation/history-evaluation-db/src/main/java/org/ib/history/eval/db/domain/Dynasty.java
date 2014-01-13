package org.ib.history.eval.db.domain;

import javax.persistence.Entity;

import org.springframework.util.Assert;

@Entity
public class Dynasty extends AbstractEntity {

    private String name;

    public Dynasty(String name) {
        Assert.hasText(name, "Dynasty name must not be null or empty!");

        this.name = name;
    }

    public Dynasty() {}

    public Dynasty getCopy() {
        return new Dynasty(this.name);
    }

    public String getName() {
        return this.name;
    }
}
