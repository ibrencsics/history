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
//        WikiPerson person = service.getPerson("Henry_VIII_of_England");
//        WikiPerson person = service.getPerson("Louis_I_of_Hungary");
//        WikiPerson person = service.getPerson("Louis_XIV_of_France");
//        WikiPerson person = service.getPerson("Anne_of_Austria");
//        WikiPerson person = service.getPerson("Empress Matilda");
//        WikiPerson person = service.getPerson("Stephen,_King_of_England");
//        WikiPerson person = service.getPerson("Charles II of England");
//        WikiPerson person = service.getPerson("James II & VII");
//        WikiPerson person = service.getPerson("Henry_V_of_England");
//        WikiPerson person = service.getPerson("Edward_VII");
//        WikiPerson person = service.getPerson("Elizabeth_II");
        WikiPerson person = service.getPerson("Edward_IV_of_England");
        System.out.println(person);

    }
}
