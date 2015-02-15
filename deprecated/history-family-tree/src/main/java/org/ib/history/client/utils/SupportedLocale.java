package org.ib.history.client.utils;

import java.util.ArrayList;
import java.util.List;

public enum SupportedLocale {

    EN(true), DE, HU;

    private boolean defaultLocale = false;

    private SupportedLocale() {
    }

    private SupportedLocale(boolean defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public static SupportedLocale getDefault() {
        for (SupportedLocale item : values()) {
            if (item.defaultLocale) return item;
        }
        return EN;
    }

    public static List<SupportedLocale> getLocalesExceptDefault() {
        List<SupportedLocale> locales = new ArrayList<SupportedLocale>(values().length-1);
        SupportedLocale defaultLocale = getDefault();

        for (SupportedLocale locale : values()) {
            if (locale != defaultLocale) {
                locales.add(locale);
            }
        }

        return locales;
    }
}
