package org.ib.history.db.neo4j;

public interface Converter<T,S> {
    T convert(S source);
}
