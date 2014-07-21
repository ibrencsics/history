package org.ib.history.commons.utils;

import com.google.gwt.user.client.rpc.IsSerializable;

public abstract class DateWrapper implements IsSerializable {
    public abstract boolean isThereMonth();
    public abstract boolean isThereDay();
    public abstract int getYear();
    public abstract int getMonth();
    public abstract int getDay();
    public abstract boolean isAD();

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(isAD() ? "" : "-");
//        sb.append(getYear());
//        if (isThereMonth()) sb.append("-" + String.format("%02d", getMonth()));
//        if (isThereDay()) sb.append("-" + String.format("%02d", getDay()));
//        return sb.toString();
//    }

    public abstract static class Builder {
        public abstract Builder year(int year);
        public abstract Builder month(int month);
        public abstract Builder day(int day);
        public abstract Builder noMonth();
        public abstract Builder noDay();
        public abstract DateWrapper build();
    }
}
