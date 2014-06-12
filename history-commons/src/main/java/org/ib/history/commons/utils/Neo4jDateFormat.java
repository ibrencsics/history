package org.ib.history.commons.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Neo4jDateFormat {

    public static <T extends DateWrapper> T parse(String stringFormat, Class<T> clazz) {
        try {
            DateWrapper dateWrapper = clazz.newInstance();

            if (clazz == FullDateWrapper.class) {
                return (T) parseFull(stringFormat);
            } else if (clazz == SimpleDateWrapper.class) {
                return (T) parseSimple(stringFormat);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }

        throw new IllegalArgumentException();
    }

    public static SimpleDateWrapper parseSimple(String stringFormat) {
        SimpleDateWrapper dateWrapper = new SimpleDateWrapper();

        if (stringFormat==null)
            return dateWrapper;

        if (stringFormat.startsWith("-")) {
            dateWrapper.setAD(false);
            stringFormat = stringFormat.substring(1);
        }

        Integer year = Integer.parseInt(stringFormat.substring(0,4));
        Integer month = Integer.parseInt(stringFormat.substring(4,6));
        Integer day = Integer.parseInt(stringFormat.substring(6,8));

        if (stringFormat.length()==8) {
            if (year==0 && month==0 && day==0) {
                dateWrapper.setYear(1);
                dateWrapper.setMonth(0);
                dateWrapper.setThereMonth(false);
                dateWrapper.setDay(1);
                dateWrapper.setThereDay(false);
            } else if (month==0 && day==0) {
                dateWrapper.setYear(year);
                dateWrapper.setMonth(0);
                dateWrapper.setThereMonth(false);
                dateWrapper.setDay(1);
                dateWrapper.setThereDay(false);
            } else if (day==0) {
                dateWrapper.setYear(year);
                dateWrapper.setMonth(month);
                dateWrapper.setDay(1);
                dateWrapper.setThereDay(false);
            } else {
                dateWrapper.setYear(year);
                dateWrapper.setMonth(month);
                dateWrapper.setDay(day);
            }
        } else {
            throw new IllegalArgumentException("date format unknown");
        }

        return dateWrapper;
    }

    public static FullDateWrapper parseFull(String stringFormat) {
        FullDateWrapper dateWrapper = new FullDateWrapper();

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

    public static String dateWrapperToString(DateWrapper dateWrapper) {
        StringBuilder sb = new StringBuilder();
        sb.append(dateWrapper.isAD() ? "" : "-");
        sb.append(dateWrapper.getYear());
        if (dateWrapper.isThereMonth()) sb.append("-" + String.format("%02d", dateWrapper.getMonth()));
        if (dateWrapper.isThereDay()) sb.append("-" + String.format("%02d", dateWrapper.getDay()));
        return sb.toString();
    }
}
