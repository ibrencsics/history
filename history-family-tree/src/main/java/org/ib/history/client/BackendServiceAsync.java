package org.ib.history.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.ib.history.commons.data.CountryDto;

import java.util.List;

public interface BackendServiceAsync {
    void getCountries(AsyncCallback<List<CountryDto>> countries);
    void addCountry(CountryDto country, AsyncCallback<Void> callback);
    void deleteCountry(CountryDto country, AsyncCallback<Void> callback);
}
