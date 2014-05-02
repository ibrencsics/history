package org.ib.history.commons.data;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocalizedCountryDto extends AbstractDto {

    private Locale defaultLocale = Locale.ENGLISH;

    private CountryDto defaultCountry;
    private Map<Locale,CountryDto> locales;

    public LocalizedCountryDto() {
        locales = new HashMap<>();
    }

    public void addLocale(Locale locale, CountryDto country) {
        if (locale.equals(defaultLocale)) {
            defaultCountry = country;
        } else {
            locales.put(locale, country);
        }
    }

    public CountryDto getDefaultCountry() {
        return defaultCountry;
    }

    public Map<Locale,CountryDto> getLocales() {
        return locales;
    }
}
