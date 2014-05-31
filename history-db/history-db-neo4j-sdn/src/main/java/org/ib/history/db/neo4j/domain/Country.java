package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Country extends AbstractEntity {

    private String name;
    private boolean defaultLocale = false;

    @Fetch
    @RelatedToVia
    private Set<Translation> locales;

    public Country() {
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

    public boolean isDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(boolean defaultLocale) {
        this.defaultLocale = defaultLocale;
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
