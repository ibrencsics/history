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
}
