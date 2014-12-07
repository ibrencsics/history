package org.ib.history.commons.tuples;

public class Tuple2<A1,A2> {
    private A1 element1;
    private A2 element2;

    protected Tuple2() {
    }

    public Tuple2(final A1 element1, final A2 element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public A1 element1() {
        return element1;
    }

    public A2 element2() {
        return element2;
    }
}
