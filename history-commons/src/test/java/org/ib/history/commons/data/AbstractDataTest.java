package org.ib.history.commons.data;

import org.junit.Test;
import static org.junit.Assert.*;

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
}
