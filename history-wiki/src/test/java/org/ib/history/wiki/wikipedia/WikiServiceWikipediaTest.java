package org.ib.history.wiki.wikipedia;

import org.ib.history.wiki.domain.WikiPerson;
import org.ib.history.wiki.service.WikiService;
import org.junit.Test;

public class WikiServiceWikipediaTest {

    @Test
    public void testGetPerson() {
        WikiService service = new WikiServiceWikipedia();
//        WikiPerson person = service.getPerson("William_the_Conqueror");
//        WikiPerson person = service.getPerson("William_III_of_England");
        WikiPerson person = service.getPerson("Henry_VIII_of_England");
        System.out.println(person);

    }
}
