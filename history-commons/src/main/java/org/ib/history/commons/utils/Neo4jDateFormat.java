package org.ib.history.commons.utils;

import org.ib.history.commons.data.FlexibleDate;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Neo4jDateFormat {

    public static FlexibleDate parse(String stringFormat) {
        FlexibleDate flexDate = new FlexibleDate();

        if (stringFormat==null)
            return flexDate;

        if (stringFormat.startsWith("-")) {
            flexDate.setAD(false);
            stringFormat = stringFormat.substring(1);
        }

        Integer year = Integer.parseInt(stringFormat.substring(0,4));
        Integer month = Integer.parseInt(stringFormat.substring(4,6));
        Integer day = Integer.parseInt(stringFormat.substring(6,8));

        if (stringFormat.length()==8) {
            if (year==0 && month==0 && day==0) {
                flexDate.setYear(1);
                flexDate.setMonth(0);
                flexDate.setThereMonth(false);
                flexDate.setDay(1);
                flexDate.setThereDay(false);
            } else if (month==0 && day==0) {
                flexDate.setYear(year);
                flexDate.setMonth(0);
                flexDate.setThereMonth(false);
                flexDate.setDay(1);
                flexDate.setThereDay(false);
            } else if (day==0) {
                flexDate.setYear(year);
                flexDate.setMonth(month);
                flexDate.setDay(1);
                flexDate.setThereDay(false);
            } else {
                flexDate.setYear(year);
                flexDate.setMonth(month);
                flexDate.setDay(day);
            }

            flexDate.setValue(dateWrapperToString(flexDate));
        } else {
            throw new IllegalArgumentException("date format unknown");
        }

        return flexDate;
    }

    public static String serialize(FlexibleDate flexDate) {
        if (flexDate==null)
            return null;

        StringBuilder sb = new StringBuilder();
        if (!flexDate.isAD()) {
            sb.append("-");
        }
        sb.append(String.format("%04d", flexDate.getYear()));
        sb.append(flexDate.isThereMonth() ? String.format("%02d", flexDate.getMonth()) : "00");
        sb.append(flexDate.isThereDay() ? String.format("%02d", flexDate.getDay()) : "00");
        return sb.toString();
    }

    public static String dateWrapperToString(FlexibleDate flexDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(flexDate.isAD() ? "" : "-");
        sb.append(flexDate.getYear());
        if (flexDate.isThereMonth()) sb.append("-" + String.format("%02d", flexDate.getMonth()));
        if (flexDate.isThereDay()) sb.append("-" + String.format("%02d", flexDate.getDay()));
        return sb.toString();
    }
}
