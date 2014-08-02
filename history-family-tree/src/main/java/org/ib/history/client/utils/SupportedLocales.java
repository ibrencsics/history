package org.ib.history.client.utils;

public enum SupportedLocales {

    EN(true), DE, HU;

    private boolean defaultLocale = false;

    private SupportedLocales() {
    }

    private SupportedLocales(boolean defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public static SupportedLocales getDefault() {
        for (SupportedLocales item : values()) {
            if (item.defaultLocale) return item;
        }
        return EN;
    }
}
