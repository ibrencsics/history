package org.ib.history.db.neo4j;

import org.ib.history.db.neo4j.domain.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

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

//    @Test
//    @Ignore
//    public void test() {
//        Person person = new Person("William I", "1028", "1087");
//
//        Person result = personRepo.save(person);
//        assertThat(result.getId(), is(notNullValue()));
//    }

    @Test
    @Ignore
    public void insertTest() {

        Calendar birth = Calendar.getInstance();
        birth.set(1028, 0, 1);
        Calendar death = Calendar.getInstance();
        death.set(1087, 0, 1);

        Person person = new Person("William I", birth.getTime(), death.getTime());
        Person resPerson = personRepo.save(person);

        Country country = new Country("England");
        Country resCountry = countryRepo.save(country);

        Calendar from = new GregorianCalendar(1066, 0, 1);
        Calendar to = new GregorianCalendar(1087, 0, 1);

        Rule rule = new Rule(person, country, from.getTime(), to.getTime());
        Rule resRule = ruleRepo.save(rule);
    }

    @Test
    @Ignore
    public void queryTest() {

//        Person person = personRepo.findOne(0L);
//        System.out.println(person.getBirthDate());
        List<PersonRepository.Ruler> rulers = personRepo.getRulers();

        for (PersonRepository.Ruler ruler : rulers) {
            System.out.println(ruler.getPerson().getName() + " : " + ruler.getCountry().getName() +
                " : " + ruler.getRule().getFromDate() + "-" + ruler.getRule().getToDate() + " : "
                    + getCalendar( ruler.getPerson().getBirthDate() ).get(Calendar.YEAR)  );
        }
    }

    @Test
    @Ignore
    public void dateTest() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1066);

        System.out.println(cal.getTime());
    }

    private Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    @Test
    public void test() {
        EndResult<Country> countries = countryRepo.findAll();

        Iterator<Country> it = countries.iterator();

        while (it.hasNext()) {
            System.out.println(it.next().getName());
        }


//        for (Country country=countries.iterator().next(); countries.iterator().hasNext(); ) {
//            System.out.println(country.getName());
//        }
    }
}
