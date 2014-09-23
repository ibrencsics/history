package org.ib.history.db.neo4j.domain;

import org.ib.history.commons.data.*;
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
                .id(person.getId())
                .name(person.getName())
                .gender(person.getGender())
                .alias(person.getAlias())
                .dateOfBirth(Neo4jDateFormat.parse(person.getDateOfBirth()))
                .dateOfDeath(Neo4jDateFormat.parse(person.getDateOfDeath()));

        for (Person parent : person.getParents()) {
//            personDataBuilder.parent(transform(parent));
            personDataBuilder.parent(new PersonData.Builder().id(parent.getId()).build());
        }

        for (Spouse spouse : person.getSpouses()) {
            SpouseData spouseData = new SpouseData.Builder()
                    .id(spouse.getId())
                    .from(Neo4jDateFormat.parse(spouse.getFromDate()))
                    .to(Neo4jDateFormat.parse(spouse.getToDate()))
                    .person1(new PersonData.Builder().id(spouse.getPerson1().getId()).build())
                    .person2(new PersonData.Builder().id(spouse.getPerson2().getId()).build())
                    .build();
            personDataBuilder.spouse(spouseData);
        }

        for (House house : person.getHouses()) {
            personDataBuilder.house(new HouseData.Builder().id(house.getId()).build());
        }

        for (Rules rules : person.getRules()) {
            RulesData.Builder rulesDataBuilder = new RulesData.Builder()
                    .id(rules.getId())
                    .title(rules.getTitle())
                    .number(rules.getNumber())
                    .from(Neo4jDateFormat.parse(rules.getFromDate()))
                    .to(Neo4jDateFormat.parse(rules.getFromDate()));

            rulesDataBuilder.person(new PersonData.Builder().id(person.getId()).build());
            rulesDataBuilder.country(new CountryData.Builder().id(rules.getCountry().getId()).build());

            personDataBuilder.rules(rulesDataBuilder.build());
        }

        for (BaseEntityWithTranslation.Translation<Person> locale : person.getLocales()) {
            personDataBuilder.locale(locale.getLang(), transform(locale.getTranslation()));
        }

        return personDataBuilder.build();
    }

    public static Person transform(PersonData personData) {
        if (personData==null)
            return null;

        Person person = new Person(
                personData.getId(),
                personData.getName(),
                personData.getGender(),
                personData.getAlias(),
                Neo4jDateFormat.serialize(personData.getDateOfBirth()),
                Neo4jDateFormat.serialize(personData.getDateOfDeath()));
        person.setDefaultLocale(true);

        for (PersonData parent : personData.getParents()) {
            person.addParent(new Person(parent.getId()));
        }

        for (SpouseData spouseData : personData.getSpouses()) {
            Person person1 = new Person(spouseData.getPerson1().getId());
            Person person2 = new Person(spouseData.getPerson2().getId());
            Spouse spouse = new Spouse(spouseData.getId(), person1, person2,
                    Neo4jDateFormat.serialize(spouseData.getFrom()), Neo4jDateFormat.serialize(spouseData.getTo()));
            person.addSpouse(spouse);
        }

        for (HouseData houseData : personData.getHouses()) {
            if (houseData.getId() != null)
                person.addHouse(new House(houseData.getId()));
        }

        for (RulesData rulesData : personData.getRules()) {
            Person ruler = new Person(rulesData.getPerson().getId());
            Country country = new Country(rulesData.getCountry().getId());
            Rules rules = new Rules(rulesData.getId(), ruler, country, rulesData.getTitle(), rulesData.getNumber(),
                    Neo4jDateFormat.serialize(rulesData.getFrom()), Neo4jDateFormat.serialize(rulesData.getTo()));
            person.addRule(rules);
        }

        for (String locale : personData.getLocales().keySet()) {
            PersonData localePersonData = personData.getLocales().get(locale);
            Person localePerson = new Person(localePersonData.getId(), localePersonData.getName(), localePersonData.getAlias());

            Person.Translation<Person> translation = new Person.Translation<Person>(person, localePerson, locale);
            person.getLocales().add(translation);
        }

        return person;
    }
}
