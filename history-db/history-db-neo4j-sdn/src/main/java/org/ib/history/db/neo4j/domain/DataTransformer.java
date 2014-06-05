package org.ib.history.db.neo4j.domain;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.Neo4jDateFormat;

public class DataTransformer {

    public static CountryData transform(Country country) {
        CountryData.Builder countryDataBuilder = new CountryData.Builder()
                .id(country.getId()).name(country.getName());

        for (Country.Translation<Country> translation : country.getLocales()) {
            countryDataBuilder.locale(
                    translation.getLang(),
                    new CountryData.Builder()
                            .id(translation.getTranslation().getId())
                            .name(translation.getTranslation().getName())
                            .build()
            );
        }

        return countryDataBuilder.build();
    }

    public static Country transform(CountryData countryData) {
        Country country = new Country();
        country.setId(countryData.getId());
        country.setName(countryData.getName());
        country.setDefaultLocale(true);

        for (String locale : countryData.getLocales().keySet()) {
            CountryData localeCountryData = countryData.getLocales().get(locale);
            Country localeCountry = new Country();
            localeCountry.setId(localeCountryData.getId());
            localeCountry.setName(localeCountryData.getName());

            Country.Translation translation = new Country.Translation(country, localeCountry, locale);
            country.getLocales().add(translation);
        }
        return country;
    }

    public static PersonData transform(Person person) {
        PersonData.Builder personDataBuilder = new PersonData.Builder()
                .id(person.getId()).name(person.getName())
                .dateOfBirth(Neo4jDateFormat.parse(person.getDateOfBirth()))
                .dateOfDeath(Neo4jDateFormat.parse(person.getDateOfDeath()));

        for (Person child : person.getChildren()) {
            personDataBuilder.child(transform(child));
        }

        return personDataBuilder.build();
    }

    public static Person transform(PersonData personData) {
        Person person = new Person(
                personData.getId(), personData.getName(),
                Neo4jDateFormat.serialize(personData.getDateOfBirth()),
                Neo4jDateFormat.serialize(personData.getDateOfDeath()));
        person.setDefaultLocale(true);

        for (String locale : personData.getLocales().keySet()) {
            PersonData localePersonData = personData.getLocales().get(locale);
            Person localePerson = new Person(localePersonData.getId(), localePersonData.getName());

            Person.Translation<Person> translation = new Person.Translation<Person>(person, localePerson, locale);
            person.getLocales().add(translation);
        }

        return person;
    }
}
