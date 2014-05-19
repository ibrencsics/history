package org.ib.history.commons.data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocalizedDto<T> extends AbstractDto {

    private T defaultLocaleElement;
    private Map<Locale,T> locales;

    public LocalizedDto() {
        locales = new HashMap<>();
    }

    public T getDefaultLocaleElement() {
        return defaultLocaleElement;
    }

    public void setDefaultLocaleElement(T defaultLocaleElement) {
        this.defaultLocaleElement = defaultLocaleElement;
    }

    public void addLocaleElement(T localeElement, Locale locale) {
        locales.put(locale, localeElement);
    }

    public T getLocaleElement(Locale locale) {
        return locales.get(locale);
    }

    public Map<Locale,T> getLocales() {
        return locales;
    }

    @Override
    public Long getId() {
        return ((AbstractDto)getDefaultLocaleElement()).getId();
    }

    @Override
    public String getName() {
        return ((AbstractDto)getDefaultLocaleElement()).getName();
    }
}
