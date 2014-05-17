package org.ib.history.db.neo4j;

import org.ib.history.commons.data.CountryDto;
import org.ib.history.commons.data.HouseDto;
import org.ib.history.commons.data.LocalizedDto;

import java.util.Locale;

public class Refdata {

    public static LocalizedDto<CountryDto> getHungary() {
        LocalizedDto<CountryDto> country = new LocalizedDto<CountryDto>();
        country.setDefaultLocaleElement(new CountryDto().withName("Hungary"));
        country.addLocaleElement(new CountryDto().withName("Ungarn"), Locale.GERMAN);
        country.addLocaleElement(new CountryDto().withName("Magyarország"), new Locale("HU"));
        return country;
    }

    public static LocalizedDto<CountryDto> getEngland() {
        LocalizedDto<CountryDto> country = new LocalizedDto<CountryDto>();
        country.setDefaultLocaleElement(new CountryDto().withName("England"));
        country.addLocaleElement(new CountryDto().withName("England"), Locale.GERMAN);
        country.addLocaleElement(new CountryDto().withName("Anglia"), new Locale("HU"));
        return country;
    }

    public static LocalizedDto<HouseDto> getNormandy() {
        LocalizedDto<HouseDto> house = new LocalizedDto<>();
        house.setDefaultLocaleElement(new HouseDto().withName("House of Normandy"));
        house.addLocaleElement(new HouseDto().withName("Normannische Dynastie"), Locale.GERMAN);
        house.addLocaleElement(new HouseDto().withName("Normandiai ház"), new Locale("HU"));
        return house;
    }
}
