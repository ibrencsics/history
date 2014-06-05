package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Country extends AbstractEntity<Country> {

    private String name;

    @Fetch
    @RelatedToVia
    private Set<Translation<Country>> locales;

    public Country() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Translation<Country>> getLocales() {
        if (locales==null) {
            locales = new HashSet<>();
        }
        return locales;
    }

    public void setLocales(Set<Translation<Country>> locales) {
        this.locales = locales;
    }

//    @RelationshipEntity(type = "TRANSLATION")
//    public static class Translation extends AbstractEntity {
//        @StartNode
//        private Country country;
//
//        @Fetch
//        @EndNode
//        private Country translation;
//
//        public Translation() {
//        }
//
//        public Translation(Country country, Country translation, String lang) {
//            this.country = country;
//            this.translation = translation;
//            this.lang = lang;
//        }
//
//        private String lang;
//
//        public Country getCountry() {
//            return country;
//        }
//
//        public Country getTranslation() {
//            return translation;
//        }
//
//        public String getLang() {
//            return lang;
//        }
//    }
}
