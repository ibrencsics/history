package org.ib.history.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.SuggestOracle;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

//@RemoteServiceRelativePath("backend")
@RemoteServiceRelativePath("springGwtServices/backendService")
public interface BackendService extends RemoteService {

    List<CountryData> getCountries(String locale);
    void addCountry(CountryData country);
    void deleteCountry(CountryData country);

    List<HouseData> getHouses();
    List<HouseData> getHousesByPattern(String pattern);
    void addHouse(HouseData house);
    void deleteHouse(HouseData house);

    List<PersonData> getPersons();
    void addPerson(PersonData person);
    void deletePerson(PersonData person);

//    SuggestOracle.Response getSuggestions(SuggestOracle.Request request);
}
