package org.ib.history.commons.utils;

import org.ib.history.commons.data.FlexibleDate;

public class GwtDateFormat {

    public static String convert(FlexibleDate flexDate) {
        if (flexDate==null) return null;

        StringBuilder sb = new StringBuilder();
        sb.append(flexDate.isAD() ? "" : "-");
        sb.append(flexDate.getYear());
        if (flexDate.isThereMonth()) sb.append("-" + flexDate.getMonth());
        if (flexDate.isThereDay()) sb.append("-" + flexDate.getDay());
        return sb.toString();
    }

    public static FlexibleDate convert(String strDate) {
        FlexibleDate.Builder flexDateBuilder = new FlexibleDate.Builder();

        String[] tokens = strDate.split("-");
        flexDateBuilder.year(Integer.valueOf(tokens[0]));
        if (tokens.length>1)
            flexDateBuilder.month(Integer.valueOf(tokens[1]));
        if (tokens.length>2)
            flexDateBuilder.day(Integer.valueOf(tokens[2]));

        return flexDateBuilder.build();
    }
}
