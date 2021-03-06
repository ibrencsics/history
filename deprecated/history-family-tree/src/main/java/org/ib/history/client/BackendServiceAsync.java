package org.ib.history.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ib.history.commons.data.*;

import java.util.List;
import java.util.Set;

public interface BackendServiceAsync {

    void getCountries(AsyncCallback<List<CountryData>> countries);
    void getCountries(int start, int length, AsyncCallback<List<CountryData>> countries);
    void getCountriesByPattern(String pattern, AsyncCallback<List<CountryData>> callback);
    void getCountriesByIds(List<CountryData> countriesOnlyIds, AsyncCallback<List<CountryData>> callback);
    void addCountry(CountryData country, AsyncCallback<Void> callback);
    void deleteCountry(CountryData country, AsyncCallback<Void> callback);

    void getHouses(AsyncCallback<List<HouseData>> callback);
    void getHouses(int start, int length, AsyncCallback<List<HouseData>> callback);
    void getHousesByPattern(String pattern, AsyncCallback<List<HouseData>> callback);
    void getHousesByIds(List<HouseData> housesOnlyIds, AsyncCallback<List<HouseData>> callback);
    void addHouse(HouseData house, AsyncCallback<Void> callback);
    void deleteHouse(HouseData house, AsyncCallback<Void> callback);

    void getPersons(AsyncCallback<List<PersonData>> persons);
    void getPersons(int start, int length, AsyncCallback<List<PersonData>> persons);
    void getPersonsByPattern(String pattern, AsyncCallback<List<PersonData>> callback);
    void getPersonsByIds(List<PersonData> personsOnlyIds, AsyncCallback<List<PersonData>> callback);
    void addPerson(PersonData person, AsyncCallback<Void> callback);
    void deletePerson(PersonData person, AsyncCallback<Void> callback);

    void setParents(PersonData person, List<PersonData> parents, AsyncCallback<Void> callback);
    void setHouses(PersonData person, List<HouseData> houses, AsyncCallback<Void> callback);
    void setSpouses(PersonData person, List<SpouseData> spouses, AsyncCallback<Void> callback);
    void setRules(PersonData person, List<RulesData> rules, AsyncCallback<Void> callback);
    void setPope(PersonData person, PopeData pope, AsyncCallback<Void> callback);

    void getPopes(AsyncCallback<List<PopeData>> callback);
    void getPopes(int start, int length, AsyncCallback<List<PopeData>> callback);
    void getPopesByPattern(String pattern, AsyncCallback<List<PopeData>> callback);
    void getPopeById(PopeData popeOnlyId, AsyncCallback<PopeData> callback);
    void addPope(PopeData popeData, AsyncCallback<Void> callback);
    void deletePope(PopeData popeData, AsyncCallback<Void> callback);
}
