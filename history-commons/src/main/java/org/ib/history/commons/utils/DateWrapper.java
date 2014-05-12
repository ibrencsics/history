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
        if (calendar==null) {
            calendar = new GregorianCalendar();
        }
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
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

    public static class Builder {
        private DateWrapper dateWrapper = new DateWrapper();

        public Builder year(int year) {
            dateWrapper.getCalendar().set(Calendar.YEAR, year);
            return this;
        }

        public Builder month(int month) {
            dateWrapper.getCalendar().set(Calendar.MONTH, month);
            return this;
        }

        public Builder day(int day) {
            dateWrapper.getCalendar().set(Calendar.DAY_OF_MONTH, day);
            return this;
        }

        public Builder noMonth() {
            dateWrapper.setThereMonth(false);
            return this;
        }

        public Builder noDay() {
            dateWrapper.setThereDay(false);
            return this;
        }

        public DateWrapper build() {
            return dateWrapper;
        }
    }
}
