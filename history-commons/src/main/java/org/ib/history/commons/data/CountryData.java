package org.ib.history.commons.data;

import java.util.Locale;

public class CountryData extends AbstractData<CountryData> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        CountryData countryData = new CountryData();

        public Builder id(Long id) {
            countryData.setId(id);
            return this;
        }

        public Builder name(String name) {
            countryData.setName(name);
            return this;
        }

        public Builder locale(String locale, CountryData localeCountryData) {
            countryData.addLocale(locale, localeCountryData);
            return this;
        }

        public CountryData build() {
            return countryData;
        }
    }
}
