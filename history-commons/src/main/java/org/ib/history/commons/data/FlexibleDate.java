package org.ib.history.commons.data;

import com.google.gwt.user.client.rpc.IsSerializable;
import org.ib.history.commons.utils.GwtDateFormat;

public class FlexibleDate implements IsSerializable {

    private boolean isThereDay = true;
    private boolean isThereMonth = true;
    private boolean isAD = true;
    private int day, month, year;
    private String value;

    public boolean isThereMonth() {
        return isThereMonth;
    }

    public boolean isThereDay() {
        return isThereDay;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class Builder {

        private FlexibleDate flexDate = new FlexibleDate();

        public FlexibleDate.Builder year(int year) {
            flexDate.setYear(year < 0 ? -year : year);
            if (year < 0) flexDate.setAD(false);
            return this;
        }

        public FlexibleDate.Builder month(int month) {
            flexDate.setMonth(month);
            return this;
        }

        public FlexibleDate.Builder day(int day) {
            flexDate.setDay(day);
            return this;
        }

        public FlexibleDate.Builder noMonth() {
            flexDate.setThereMonth(false);
            return this;
        }

        public FlexibleDate.Builder noDay() {
            flexDate.setThereDay(false);
            return this;
        }

        public FlexibleDate build() {
            return flexDate;
        }
    }

    @Override
    public String toString() {
        return GwtDateFormat.convert(this);
    }
}
