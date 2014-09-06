package org.ib.history.db.neo4j.domain;

import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;
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

        for (Person parent : person.getParents()) {
            personDataBuilder.parent(transform(parent));
        }

        for (AbstractEntity.Translation<Person> locale : person.getLocales()) {
            personDataBuilder.locale(locale.getLang(), transform(locale.getTranslation()));
        }

        if (person.getHouse() != null) {
            personDataBuilder.house(transform(person.getHouse()));
        }

        // Only the Ruler ID considered (lazy loading)
        if (person.getJobs() != null) {
            for (Ruler ruler : person.getJobs()) {
                personDataBuilder.ruler(new RulerData.Builder().id(ruler.getId()).build());
            }
        }

        return personDataBuilder.build();
    }

    public static Person transform(PersonData personData) {
        if (personData==null)
            return null;

        House house=null;
        if (personData.getHouse()!=null) {
            house = new House();
            house.setId(personData.getHouse().getId());
        }

        Person person = new Person(
                personData.getId(), personData.getName(),
                Neo4jDateFormat.serialize(personData.getDateOfBirth()),
                Neo4jDateFormat.serialize(personData.getDateOfDeath()),
                house);
        person.setDefaultLocale(true);

        for (PersonData parent : personData.getParents()) {
            person.addParent(transform(parent));
        }

        // only the Ruler Id considered (lazy loading)
        if (personData.getRulers() !=  null) {
            for(RulerData rulerData : personData.getRulers()) {
                Ruler ruler = new Ruler();
                ruler.setId(rulerData.getId());
                person.addJob(ruler);
            }
        }

        for (String locale : personData.getLocales().keySet()) {
            PersonData localePersonData = personData.getLocales().get(locale);
            Person localePerson = new Person(localePersonData.getId(), localePersonData.getName());

            Person.Translation<Person> translation = new Person.Translation<Person>(person, localePerson, locale);
            person.getLocales().add(translation);
        }

        return person;
    }

    public static RulerData transform(Ruler ruler) {
        RulerData.Builder rulerDataBuilder = new RulerData.Builder()
                .id(ruler.getId()).name(ruler.getName());

        if (ruler.getAlias() != null) {
            rulerDataBuilder.alias(ruler.getAlias());
        }
        if (ruler.getTitle() != null) {
            rulerDataBuilder.title(ruler.getTitle());
        }

        for (Rules rules : ruler.getAllRules()) {
            RulerData.RulesData.Builder rulesDataBuilder = new RulerData.RulesData.Builder().id(rules.getId());
            if (rules.getFromDate() != null) {
                rulesDataBuilder.from(Neo4jDateFormat.parse(rules.getFromDate()));
            }
            if (rules.getToDate() != null) {
                rulesDataBuilder.to(Neo4jDateFormat.parse(rules.getToDate()));
            }
            if (rules.getCountry() != null) {
                rulesDataBuilder.country(transform(rules.getCountry()));
            }

            rulerDataBuilder.rule(rulesDataBuilder.build());
        }

        if (ruler.getPerson() != null) {
            rulerDataBuilder.person( transform(ruler.getPerson()) );
        }

        for (AbstractEntity.Translation<Ruler> locale : ruler.getLocales()) {
            rulerDataBuilder.locale(locale.getLang(), transform(locale.getTranslation()));
        }

        return rulerDataBuilder.build();
    }

    public static Ruler transform(RulerData rulerData) {
        Ruler ruler = new Ruler(
                rulerData.getId(),
                rulerData.getName(),
                rulerData.getAlias(),
                rulerData.getTitle()
        );
        ruler.setDefaultLocale(true);

        for (RulerData.RulesData rulesData : rulerData.getRules()) {
            Rules rules = new Rules(
                    rulesData.getId(),
                    ruler,
                    transform(rulesData.getCountry()),
                    Neo4jDateFormat.serialize(rulesData.getFrom()),
                    Neo4jDateFormat.serialize(rulesData.getTo())
            );

            ruler.addRules(rules);
        }

        if (rulerData.getPerson() != null) {
            ruler.setPerson(DataTransformer.transform(rulerData.getPerson()));
        }

        for (String locale : rulerData.getLocales().keySet()) {
            RulerData localeRulerData = rulerData.getLocales().get(locale);
            Ruler localeRuler = new Ruler(localeRulerData.getId(), localeRulerData.getName(), localeRulerData.getAlias(), localeRulerData.getTitle());

            Ruler.Translation<Ruler> translation = new AbstractEntity.Translation<Ruler>(ruler, localeRuler, locale);
            ruler.getLocales().add(translation);
        }

        return ruler;
    }
}
