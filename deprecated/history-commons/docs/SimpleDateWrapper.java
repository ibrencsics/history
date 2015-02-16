package org.ib.history.commons.utils;

public class SimpleDateWrapper extends DateWrapper {

    private boolean isThereDay = true;
    private boolean isThereMonth = true;
    private boolean isAD = true;
    private int day, month, year;

    @Override
    public boolean isThereMonth() {
        return isThereMonth;
    }

    @Override
    public boolean isThereDay() {
        return isThereDay;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public boolean isAD() {
        return isAD;
    }


    public void setThereDay(boolean thereDay) {
        isThereDay = thereDay;
    }

    public void setThereMonth(boolean thereMonth) {
        isThereMonth = thereMonth;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAD(boolean AD) {
        isAD = AD;
    }

    public static class Builder extends DateWrapper.Builder {

        private SimpleDateWrapper dateWrapper = new SimpleDateWrapper();

        @Override
        public DateWrapper.Builder year(int year) {
            dateWrapper.setYear(year < 0 ? -year : year);
            if (year < 0) dateWrapper.setAD(false);
            return this;
        }

        @Override
        public DateWrapper.Builder month(int month) {
            dateWrapper.setMonth(month);
            return this;
        }

        @Override
        public DateWrapper.Builder day(int day) {
            dateWrapper.setDay(day);
            return this;
        }

        @Override
        public DateWrapper.Builder noMonth() {
            dateWrapper.setThereMonth(false);
            return this;
        }

        @Override
        public DateWrapper.Builder noDay() {
            dateWrapper.setThereDay(false);
            return this;
        }

        @Override
        public DateWrapper build() {
            return dateWrapper;
        }
    }
}
