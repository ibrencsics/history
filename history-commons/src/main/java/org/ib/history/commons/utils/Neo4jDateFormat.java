package org.ib.history.commons.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Neo4jDateFormat {

    public static DateWrapper parse(String stringFormat) {
        DateWrapper dateWrapper = new DateWrapper();

        if (stringFormat==null)
            return dateWrapper;

        Calendar calendar = GregorianCalendar.getInstance();

        if (stringFormat.startsWith("-")) {
            calendar.set(Calendar.ERA, GregorianCalendar.BC);
            stringFormat = stringFormat.substring(1);
        } else {
            calendar.set(Calendar.ERA, GregorianCalendar.AD);
        }

        Integer year = Integer.parseInt(stringFormat.substring(0,4));
        Integer month = Integer.parseInt(stringFormat.substring(4,6));
        Integer day = Integer.parseInt(stringFormat.substring(6,8));

        if (stringFormat.length()==8) {
            if (year==0 && month==0 && day==0) {
                // TODO
                calendar.set(Calendar.YEAR, 1);
                calendar.set(Calendar.MONTH, 0);
                dateWrapper.setThereMonth(false);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                dateWrapper.setThereDay(false);
            } else if (month==0 && day==0) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, 0);
                dateWrapper.setThereMonth(false);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                dateWrapper.setThereDay(false);
            } else if (day==0) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                dateWrapper.setThereDay(false);
            } else {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
        }
        } else {
            throw new IllegalArgumentException("date format unknown");
        }

        dateWrapper.setCalendar(calendar);
        return dateWrapper;
    }

    public static String serialize(DateWrapper dateWrapper) {
        if (dateWrapper==null)
            return null;

        StringBuilder sb = new StringBuilder();
        if (!dateWrapper.isAD()) {
            sb.append("-");
        }
        sb.append(String.format("%04d", dateWrapper.getYear()));
        sb.append(dateWrapper.isThereMonth() ? String.format("%02d", dateWrapper.getMonth()) : "00");
        sb.append(dateWrapper.isThereDay() ? String.format("%02d", dateWrapper.getDay()) : "00");
        return sb.toString();
    }
}
