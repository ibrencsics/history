package org.ib.history.client.presenters;

import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

public interface PersonPresenter extends CrudPresenter<PersonData> {
    void setHouseSuggestions(String pattern, AsyncCallback<List<HouseData>> callback);
}
