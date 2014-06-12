package org.ib.history.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

public interface BackendServiceAsync {

    void getCountries(String locale, AsyncCallback<List<CountryData>> countries);
    void addCountry(CountryData country, AsyncCallback<Void> callback);
    void deleteCountry(CountryData country, AsyncCallback<Void> callback);

    void getPersons(AsyncCallback<List<PersonData>> persons);
    void addPerson(PersonData person, AsyncCallback<Void> callback);
    void deletePerson(PersonData person, AsyncCallback<Void> callback);
}
