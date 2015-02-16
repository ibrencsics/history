package org.ib.history.commons.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FullDateWrapper extends DateWrapper {

    private Calendar calendar;
    private boolean isThereMonth = true;
    private boolean isThereDay = true;

    @Override
    public boolean isThereMonth() {
        return isThereMonth;
    }

    public void setThereMonth(boolean thereMonth) {
        isThereMonth = thereMonth;
    }

    @Override
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

    @Override
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public int getMonth() {
        return calendar.get(Calendar.MONTH);
    }

    @Override
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean isAD() {
        return calendar.get(Calendar.ERA) == GregorianCalendar.AD;
    }

    public static class Builder extends DateWrapper.Builder {

        private FullDateWrapper dateWrapper = new FullDateWrapper();

        @Override
        public Builder year(int year) {
            dateWrapper.getCalendar().set(Calendar.YEAR, year);
            return this;
        }

        @Override
        public Builder month(int month) {
            dateWrapper.getCalendar().set(Calendar.MONTH, month);
            return this;
        }

        @Override
        public Builder day(int day) {
            dateWrapper.getCalendar().set(Calendar.DAY_OF_MONTH, day);
            return this;
        }

        @Override
        public Builder noMonth() {
            dateWrapper.setThereMonth(false);
            return this;
        }

        @Override
        public Builder noDay() {
            dateWrapper.setThereDay(false);
            return this;
        }

        @Override
        public FullDateWrapper build() {
            return dateWrapper;
        }
    }
}
