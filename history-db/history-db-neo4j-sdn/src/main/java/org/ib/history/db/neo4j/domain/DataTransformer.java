package org.ib.history.db.neo4j.domain;

import org.ib.history.commons.data.CountryData;

public class DataTransformer {

    public static CountryData transform(Country country) {
        CountryData.Builder countryDataBuilder = new CountryData.Builder()
                .id(country.getId()).name(country.getName());

        for (Country.Translation translation : country.getLocales()) {
            countryDataBuilder.locale(
                    translation.getLang(),
                    new CountryData.Builder()
                            .id(translation.getTranslation().getId())
                            .name(translation.getTranslation().getName())
                            .build()
            );
        }

        return countryDataBuilder.build();
    }

    public static Country transform(CountryData countryData) {
        Country country = new Country();
        country.setId(countryData.getId());
        country.setName(countryData.getName());
        country.setDefaultLocale(true);

        for (String locale : countryData.getLocales().keySet()) {
            CountryData localeCountryData = countryData.getLocales().get(locale);
            Country localeCountry = new Country();
            localeCountry.setId(localeCountryData.getId());
            localeCountry.setName(localeCountryData.getName());

            Country.Translation translation = new Country.Translation(country, localeCountry, locale);
            country.getLocales().add(translation);
        }
        return country;
    }
}
