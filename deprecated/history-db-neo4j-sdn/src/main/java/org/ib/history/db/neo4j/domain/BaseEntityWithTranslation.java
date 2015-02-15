package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

public abstract class BaseEntityWithTranslation<T extends BaseEntity> extends BaseEntity {

    private boolean defaultLocale = false;

    public boolean isDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(boolean defaultLocale) {
        this.defaultLocale = defaultLocale;
    }


    @RelationshipEntity(type = "TRANSLATION")
    public static class Translation<T extends BaseEntity> extends BaseEntity {
        @StartNode
        private T defaultEntity;

        @Fetch
        @EndNode
        private T translatedEntity;

        public Translation() {
        }

        public Translation(T defaultEntity, T translatedEntity, String lang) {
            this.defaultEntity = defaultEntity;
            this.translatedEntity = translatedEntity;
            this.lang = lang;
        }

        private String lang;

        public T getDefault() {
            return defaultEntity;
        }

        public T getTranslation() {
            return translatedEntity;
        }

        public String getLang() {
            return lang;
        }
    }
}

