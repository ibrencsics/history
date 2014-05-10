package org.ib.history.commons.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateWrapper {

    private Calendar calendar;
    private boolean isThereMonth = true;
    private boolean isThereDay = true;

    public boolean isThereMonth() {
        return isThereMonth;
    }

    public void setThereMonth(boolean thereMonth) {
        isThereMonth = thereMonth;
    }

    public boolean isThereDay() {
        return isThereDay;
    }

    public void setThereDay(boolean thereDay) {
        isThereDay = thereDay;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public boolean isAD() {
        return calendar.get(Calendar.ERA) == GregorianCalendar.AD;
    }

    public String asString() {
        StringBuilder sb = new StringBuilder();
        sb.append(isAD() ? "" : "-");
        sb.append(calendar.get(Calendar.YEAR));
        if (isThereMonth) sb.append("-" + String.format("%02d", calendar.get(Calendar.MONTH)));
        if (isThereDay) sb.append("-" + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        return sb.toString();
    }
}
