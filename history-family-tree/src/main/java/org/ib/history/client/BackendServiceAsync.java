package org.ib.history.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;

import java.util.List;
import java.util.Set;

public interface BackendServiceAsync {

    void getCountries(String locale, AsyncCallback<List<CountryData>> countries);
    void getCountriesByPattern(String pattern, AsyncCallback<List<CountryData>> callback);
    void addCountry(CountryData country, AsyncCallback<Void> callback);
    void deleteCountry(CountryData country, AsyncCallback<Void> callback);

    void getHouses(AsyncCallback<List<HouseData>> callback);
    void getHousesByPattern(String pattern, AsyncCallback<List<HouseData>> callback);
    void addHouse(HouseData house, AsyncCallback<Void> callback);
    void deleteHouse(HouseData house, AsyncCallback<Void> callback);

    void getPersons(AsyncCallback<List<PersonData>> persons);
    void getPersonsByPattern(String pattern, AsyncCallback<List<PersonData>> callback);
    void getPersonsByIds(List<PersonData> personsOnlyIds, AsyncCallback<List<PersonData>> callback);
    void addPerson(PersonData person, AsyncCallback<Void> callback);
    void deletePerson(PersonData person, AsyncCallback<Void> callback);
}
