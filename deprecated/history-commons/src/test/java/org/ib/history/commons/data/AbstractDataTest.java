package org.ib.history.commons.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractDataTest {

    @Test
    public void countryDataTest() {
        CountryData countryData =
                new CountryData.Builder().id(0L).name("Hungary")
                .locale("DE", new CountryData.Builder().name("Ungarn").build())
                .locale("HU", new CountryData.Builder().name("Magyarország").build())
                .build();

        assertEquals(countryData.getLocales().entrySet().size(), 2);
        assertEquals(countryData.getLocale("DE").getName(), "Ungarn");
    }

    @Test
    public void personDataTest() {
        PersonData personData =
                new PersonData.Builder().id(0L).name("William")
                .dateOfDeath(new FlexibleDate.Builder().year(1087).build())
                .parent(new PersonData.Builder().id(1L).name("William").build())
                .locale("DE", new PersonData.Builder().name("Wilhelm").build())
                .locale("HU", new PersonData.Builder().name("Vilmos").build())
                .build();

        assertEquals(personData.getLocales().entrySet().size(), 2);
        assertEquals(personData.getLocale("DE").getName(), "Wilhelm");
    }
}
