package org.ib.history.db.neo4j;

import org.ib.history.commons.data.*;

import java.util.List;
import java.util.Locale;

public interface Neo4jServiceDeprecated {

    public static Locale DEFAULT_LOCALE = Locale.ENGLISH;

    List<CountryDto> getCountries();
    List<CountryDto> getCountries(Locale locale);
    void putCountry(CountryDto defaultCountry);
    void putCountry(CountryDto defaultCountry, CountryDto localeCountry, Locale locale);
    void putCountry(LocalizedDto<CountryDto> country);
    void deleteCountry(CountryDto country);

    List<HouseDto> getHouses();
    List<HouseDto> getHouses(Locale locale);
    void putHouse(HouseDto house);
    void putHouse(HouseDto defaultHouse, HouseDto localeHouse, Locale locale);
    void putHouse(LocalizedDto<HouseDto> house);
    void deleteHouse(HouseDto house);

    List<PersonDto> getPeople();
    List<PersonDto> getPeople(Locale locale);
    void putPerson(PersonDto person);
    void putPerson(PersonDto defaultPerson, PersonDto localePerson, Locale locale);
    void putPerson(LocalizedDto<PersonDto> person);
    void deletePerson(PersonDto person);

    List<RulerDto> getRulers();
    List<RulerDto> getRulers(Locale locale);
    void putRuler(RulerDto ruler);
}
