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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple2 tuple2 = (Tuple2) o;

        if (element1 != null ? !element1.equals(tuple2.element1) : tuple2.element1 != null) return false;
        if (element2 != null ? !element2.equals(tuple2.element2) : tuple2.element2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = element1 != null ? element1.hashCode() : 0;
        result = 31 * result + (element2 != null ? element2.hashCode() : 0);
        return result;
    }
}
