package org.ib.history.client.presenters;

import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.commons.data.*;

import java.util.List;

public interface PersonPresenter extends CrudPresenter<PersonData> {
    void setPersonSuggestions(String pattern, AsyncCallback<List<PersonData>> callback);
    void getPersonsByIds(List<PersonData> personsIdOnly, AsyncCallback<List<PersonData>> callback);
    void setParents(PersonData person, List<PersonData> parents, AsyncCallback<Void> callback);

    void setHouseSuggestions(String pattern, AsyncCallback<List<HouseData>> callback);
    void getHousesByIds(List<HouseData> housesIdOnly, AsyncCallback<List<HouseData>> callback);
    void setHouses(PersonData person, List<HouseData> houses, AsyncCallback<Void> callback);

    void setSpouses(PersonData person, List<SpouseData> spouses, AsyncCallback<Void> callback);

    void setRules(PersonData person, List<RulesData> rules, AsyncCallback<Void> callback);

    void setCountrySuggestions(String pattern, AsyncCallback<List<CountryData>> callback);
    void getCountriesByIds(List<CountryData> countryIdOnly, AsyncCallback<List<CountryData>> callback);

    void setPopeSuggestions(String pattern, AsyncCallback<List<PopeData>> callback);
    void getPopeByIds(PopeData popeIdOnly, AsyncCallback<PopeData> callback);
    void setPope(PersonData person, PopeData pope, AsyncCallback<Void> callback);
}
