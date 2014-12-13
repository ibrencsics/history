package org.ib.history.commons.data;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

public class FlexibleDate implements IsSerializable {

    enum MonthName {
        JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5), JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10), NOVEMBER(11), DECEMBER(12);

        final int num;

        MonthName(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }

    private boolean isThereDay = true;
    private boolean isThereMonth = true;
    private boolean isAD = true;
    private boolean isCirca = false;
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

    public boolean isCirca() {
        return isCirca;
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

    public void setCirca(boolean isCirca) {
        this.isCirca = isCirca;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class Builder {

        private FlexibleDate flexDate = new FlexibleDate();

        public Builder year(String year) {
            return year(Integer.parseInt(year));
        }

        public FlexibleDate.Builder year(int year) {
            flexDate.setYear(year < 0 ? -year : year);
            if (year < 0) flexDate.setAD(false);
            return this;
        }

        public Builder monthByName(String monthName) {
            MonthName monthNameEnum = MonthName.valueOf(monthName.toUpperCase());
            month(monthNameEnum.getNum());
            return this;
        }

        public FlexibleDate.Builder month(int month) {
            flexDate.setMonth(month);
            return this;
        }

        public Builder day(String day) {
            return day(Integer.parseInt(day));
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

        public Builder circa() {
            flexDate.setCirca(true);
            return this;
        }

        public Builder fromList(List<String> yearMonthDay) {
            year(Integer.parseInt(yearMonthDay.get(0)));
            month(Integer.parseInt(yearMonthDay.get(1)));
            day(Integer.parseInt(yearMonthDay.get(2)));
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
