package org.ib.history.eval.db.domain;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Person extends AbstractEntity {

    private String name;

    private Dynasty dynasty;

    private Date dateOfBirth;

    private Date dateOfDeath;
}
