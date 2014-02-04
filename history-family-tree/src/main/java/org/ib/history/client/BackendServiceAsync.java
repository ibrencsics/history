package org.ib.history.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface BackendServiceAsync {
    void getCountries(AsyncCallback<List<String>> countries);
}
