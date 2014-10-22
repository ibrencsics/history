package org.ib.history.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.SuggestOracle;
import org.ib.history.commons.data.*;

import java.util.List;
import java.util.Set;

//@RemoteServiceRelativePath("backend")
@RemoteServiceRelativePath("springGwtServices/backendService")
public interface BackendService extends RemoteService {

    List<CountryData> getCountries();
    List<CountryData> getCountries(int start, int length);
    List<CountryData> getCountriesByPattern(String pattern);
    List<CountryData> getCountriesByIds(List<CountryData> countriesOnlyIds);
    void addCountry(CountryData country);
    void deleteCountry(CountryData country);

    List<HouseData> getHouses();
    List<HouseData> getHouses(int start, int length);
    List<HouseData> getHousesByPattern(String pattern);
    List<HouseData> getHousesByIds(List<HouseData> housesOnlyIds);
    void addHouse(HouseData house);
    void deleteHouse(HouseData house);

    List<PersonData> getPersons();
    List<PersonData> getPersons(int start, int length);
    List<PersonData> getPersonsByPattern(String pattern);
    List<PersonData> getPersonsByIds(List<PersonData> personsOnlyIds);
    void addPerson(PersonData person);
    void deletePerson(PersonData person);

    void setParents(PersonData person, List<PersonData> parents);
    void setHouses(PersonData person, List<HouseData> houses);
    void setSpouses(PersonData person, List<SpouseData> spouses);
    void setRules(PersonData person, List<RulesData> rules);
    void setPope(PersonData person, PopeData pope);

    List<PopeData> getPopes();
    List<PopeData> getPopes(int start, int length);
    List<PopeData> getPopesByPattern(String pattern);
    PopeData getPopeById(PopeData popeOnlyId);
    void addPope(PopeData popeData);
    void deletePope(PopeData popeData);
}
