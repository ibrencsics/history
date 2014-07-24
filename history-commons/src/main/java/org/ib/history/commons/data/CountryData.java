package org.ib.history.commons.data;

import java.util.Map;

public class CountryData extends AbstractData<CountryData> {

//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CountryData{ id=" + getId() + " name=" + getName() + " }");
        for (String locale : getLocales().keySet()) {
            CountryData localeCountry = getLocales().get(locale);
            sb.append("\n\tid=" + localeCountry.getId() + " name=" + localeCountry.getName() + " lang=" + locale);
        }

        return sb.toString();
    }

    public static class Builder{
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
