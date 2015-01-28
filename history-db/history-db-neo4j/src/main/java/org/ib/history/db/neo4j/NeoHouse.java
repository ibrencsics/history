package org.ib.history.db.neo4j;

public class NeoHouse extends NeoBaseData {

    @Override
    public String toString() {
        return "\nNeoHouse{" +
                "wikiPage='" + getWikiPage() +
                ", name='" + getName();

    }
}
