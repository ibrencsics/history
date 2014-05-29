package org.ib.history.db.neo4j.domain;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.CountryDto;
import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NodeEntity
public class Country extends AbstractEntity {

    private String name;

    @Fetch
    @RelatedToVia
    private Set<Translation> locales;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(CountryDto countryDto) {
        this.setId(countryDto.getId());
        this.setName(countryDto.getName());
    }

    public Country(CountryData countryData) {
        this.setId(countryData.getId());
        this.setName(countryData.getName());

        for (String locale : countryData.getLocales().keySet()) {
            CountryData localeCountryData = countryData.getLocales().get(locale);
            Country localeCountry = new Country();
            localeCountry.setName(localeCountryData.getName());

            Translation translation = new Translation(this, localeCountry, locale);
            getLocales().add(translation);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Translation> getLocales() {
        if (locales==null) {
            locales = new HashSet<>();
        }
        return locales;
    }

    public void setLocales(Set<Translation> locales) {
        this.locales = locales;
    }

    @RelationshipEntity(type = "TRANSLATION")
    public static class Translation extends AbstractEntity {
        @StartNode
        private Country country;

        @Fetch
        @EndNode
        private Country translation;

        public Translation() {
        }

        public Translation(Country country, Country translation, String lang) {
            this.country = country;
            this.translation = translation;
            this.lang = lang;
        }

        private String lang;

        public Country getCountry() {
            return country;
        }

        public Country getTranslation() {
            return translation;
        }

        public String getLang() {
            return lang;
        }
    }
}
