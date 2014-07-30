package org.ib.history.db.neo4j.domain;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
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

    public static HouseData transform(House house) {
        HouseData.Builder houseDataBuilder = new HouseData.Builder()
                .id(house.getId()).name(house.getName());

        for (House.Translation<House> translation : house.getLocales()) {
            houseDataBuilder.locale(
                    translation.getLang(),
                    new HouseData.Builder()
                            .id(translation.getTranslation().getId())
                            .name(translation.getTranslation().getName())
                            .build()
            );
        }

        return houseDataBuilder.build();
    }

    public static House transform(HouseData houseData) {
        House house = new House();
        house.setId(houseData.getId());
        house.setName(houseData.getName());
        house.setDefaultLocale(true);

        for (String locale : houseData.getLocales().keySet()) {
            HouseData localeHouseData = houseData.getLocales().get(locale);
            House localeHouse = new House();
            localeHouse.setId(localeHouseData.getId());
            localeHouse.setName(localeHouseData.getName());

            House.Translation translation = new House.Translation(house, localeHouse, locale);
            house.getLocales().add(translation);
        }
        return house;
    }

    public static PersonData transform(Person person) {
        PersonData.Builder personDataBuilder = new PersonData.Builder()
                .id(person.getId()).name(person.getName());

        if (person.getDateOfBirth() != null)
            personDataBuilder.dateOfBirth(Neo4jDateFormat.parse(person.getDateOfBirth()));
        if (person.getDateOfDeath() != null)
            personDataBuilder.dateOfDeath(Neo4jDateFormat.parse(person.getDateOfDeath()));

        for (Person child : person.getChildren()) {
            personDataBuilder.child(transform(child));
        }

        for (AbstractEntity.Translation<Person> locale : person.getLocales()) {
            personDataBuilder.locale(locale.getLang(), transform(locale.getTranslation()));
        }

        return personDataBuilder.build();
    }

    public static Person transform(PersonData personData) {
        Person person = new Person(
                personData.getId(), personData.getName(),
                Neo4jDateFormat.serialize(personData.getDateOfBirth()),
                Neo4jDateFormat.serialize(personData.getDateOfDeath()));
        person.setDefaultLocale(true);

        for (PersonData child : personData.getChildren()) {
            person.addChild(transform(child));
        }

        for (String locale : personData.getLocales().keySet()) {
            PersonData localePersonData = personData.getLocales().get(locale);
            Person localePerson = new Person(localePersonData.getId(), localePersonData.getName());

            Person.Translation<Person> translation = new Person.Translation<Person>(person, localePerson, locale);
            person.getLocales().add(translation);
        }

        return person;
    }
}
