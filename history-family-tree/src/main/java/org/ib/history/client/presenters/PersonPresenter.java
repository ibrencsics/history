package org.ib.history.client.presenters;

import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

public interface PersonPresenter extends CrudPresenter<PersonData> {
    void setPersonSuggestions(String pattern, AsyncCallback<List<PersonData>> callback);
    void getPersonsByIds(List<PersonData> personsIdOnly, AsyncCallback<List<PersonData>> callback);
    void setParents(PersonData person, List<PersonData> parents, AsyncCallback<Void> callback);

    void setHouseSuggestions(String pattern, AsyncCallback<List<HouseData>> callback);
    void getHousesByIds(List<HouseData> housesIdOnly, AsyncCallback<List<HouseData>> callback);
}
