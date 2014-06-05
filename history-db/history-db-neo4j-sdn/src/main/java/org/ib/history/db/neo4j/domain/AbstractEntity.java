package org.ib.history.db.neo4j.domain;

import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractEntity<T extends AbstractEntity> {

    @GraphId
    private Long id;

    private boolean defaultLocale = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(boolean defaultLocale) {
        this.defaultLocale = defaultLocale;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (id == null || obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        return id.equals(((AbstractEntity) obj).id);

    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }


    @RelationshipEntity(type = "TRANSLATION")
    public static class Translation<T extends AbstractEntity> extends AbstractEntity {
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

