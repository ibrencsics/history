package org.ib.history.db.neo4j;

import org.ib.history.db.neo4j.domain.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:server-appcontext.xml")
public class ServerTest {

    @Autowired
    PersonRepository personRepo;

    @Autowired
    CountryRepository countryRepo;

    @Autowired
    RuleRepository ruleRepo;

    @Test
    @Ignore
    public void test() {
        Person person = new Person("William I", "1028", "1087");

        Person result = personRepo.save(person);
        assertThat(result.getId(), is(notNullValue()));
    }

    @Test
    @Ignore
    public void test2() {
        Person person = new Person("William I", "1028", "1087");
        Person resPerson = personRepo.save(person);

        Country country = new Country("England");
        Country resCountry = countryRepo.save(country);

        Rule rule = new Rule(person, country, "1066", "1087");
        Rule resRule = ruleRepo.save(rule);
    }

    @Test
    public void queryTest() {

//        Person person = personRepo.findOne(2L);
//        System.out.println(person.getBirthDate());
        List<PersonRepository.Ruler> rulers = personRepo.getRulers();

        for (PersonRepository.Ruler ruler : rulers) {
            System.out.println(ruler.getPerson().getName() + " : " + ruler.getCountry().getName() +
                " : " + ruler.getRule().getFromDate() + "-" + ruler.getRule().getToDate());
        }
    }
}
