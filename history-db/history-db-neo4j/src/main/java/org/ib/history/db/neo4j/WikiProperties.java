package org.ib.history.db.neo4j;

public enum WikiProperties {
    STATUS("status"),
    WIKI_PAGE("wikiPage"),
    NAME("name"),
    DATE_OF_BIRTH("dateOfBirth"),
    DATE_OF_DEATH("dateOfDeath"),
    GENDER("gender");

    private final String propertyName;

    WikiProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
