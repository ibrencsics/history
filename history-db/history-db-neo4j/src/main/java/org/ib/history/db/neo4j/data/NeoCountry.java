package org.ib.history.db.neo4j.data;

public class NeoCountry extends NeoBaseData {

    @Override
    public String toString() {
        return "\nNeoCountry{" +
                "wikiPage='" + getWikiPage() +
                ", name='" + getName();

    }
}
