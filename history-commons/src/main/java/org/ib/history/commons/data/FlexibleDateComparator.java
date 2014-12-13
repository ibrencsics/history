package org.ib.history.commons.data;

import java.util.Comparator;

public class FlexibleDateComparator implements Comparator<FlexibleDate> {

    @Override
    public int compare(FlexibleDate o1, FlexibleDate o2) {
        return o1.getYear() - o2.getYear();
    }
}
