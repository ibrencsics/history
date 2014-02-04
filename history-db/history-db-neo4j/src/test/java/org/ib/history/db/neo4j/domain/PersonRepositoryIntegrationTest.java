package org.ib.history.db.neo4j.domain;

import org.ib.history.db.neo4j.AbstractIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class PersonRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;

    @Test
    @Ignore
    public void savesPersonCorrectly() {
//        Person person = new Person("William I", "1028", "1087");
//
//        Person result = repository.save(person);
//        assertThat(result.getId(), is(notNullValue()));
    }

    @Test
    @Ignore
    public void readsPersonByName() {
//        Person person = new Person("William I", "1028", "1087");
//
//        repository.save(person);
//
//        Person result = repository.findByName("William I");
//        assertThat(result, is(person));
    }
}
