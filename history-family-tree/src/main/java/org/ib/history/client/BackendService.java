package org.ib.history.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.SuggestOracle;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;

import java.util.List;
import java.util.Set;

//@RemoteServiceRelativePath("backend")
@RemoteServiceRelativePath("springGwtServices/backendService")
public interface BackendService extends RemoteService {

    List<CountryData> getCountries(String locale);
    List<CountryData> getCountriesByPattern(String pattern);
    void addCountry(CountryData country);
    void deleteCountry(CountryData country);

    List<HouseData> getHouses();
    List<HouseData> getHousesByPattern(String pattern);
    void addHouse(HouseData house);
    void deleteHouse(HouseData house);

    List<PersonData> getPersons();
    PersonData getPerson(RulerData ruler);
    List<PersonData> getPersonsByPattern(String pattern);
    List<PersonData> getPersonsByIds(List<PersonData> personsOnlyIds);
    void addPerson(PersonData person);
    void deletePerson(PersonData person);

    Set<RulerData> getRulers();
    void addRuler(RulerData ruler);
    void deleteRuler(RulerData ruler);

//    SuggestOracle.Response getSuggestions(SuggestOracle.Request request);
}
