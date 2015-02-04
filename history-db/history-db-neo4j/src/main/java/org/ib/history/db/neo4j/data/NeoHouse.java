package org.ib.history.db.neo4j.data;

public class NeoHouse extends NeoBaseData {

    @Override
    public String toString() {
        return "\nNeoHouse{" +
                "wikiPage='" + getWikiPage() +
                ", name='" + getName();

    }
}
