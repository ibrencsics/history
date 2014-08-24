package org.ib.history.client.presenters;

import org.ib.history.client.utils.AsyncCallback;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.data.RulerData;

import java.util.List;

public interface RulerPresenter extends CrudPresenter<RulerData> {
    void setPersonSuggestions(String pattern, AsyncCallback<List<PersonData>> callback);
    void setCountrySuggestions(String pattern, AsyncCallback<List<CountryData>> callback);
}
