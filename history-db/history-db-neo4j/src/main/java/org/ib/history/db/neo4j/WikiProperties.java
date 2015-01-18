package org.ib.history.db.neo4j;

public enum WikiProperties {
    FULL("full"),
    WIKI_PAGE("wikiPage"),
    NAME("name"),
    DATE_OF_BIRTH("dateOfBirth"),
    DATE_OF_DEATH("dateOfDeath");

    private final String propertyName;

    WikiProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
