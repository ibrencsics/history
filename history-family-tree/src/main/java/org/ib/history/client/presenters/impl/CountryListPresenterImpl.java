package org.ib.history.client.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.views.CountryListView;

import java.util.List;

public class CountryListPresenterImpl implements CountryListPresenter {

    private final CountryListView countryListView;

    private BackendServiceAsync backendService;

    @Inject
    public CountryListPresenterImpl(CountryListView countryListView) {
        this.countryListView = countryListView;
        bind();
    }

    @Inject
    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(countryListView.asWidget());
        getCountries();
    }

    private void getCountries() {
        backendService.getCountries(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log(throwable.getMessage());
            }

            @Override
            public void onSuccess(List<String> countries) {
                fillTable(countries);
            }
        });
    }

    private void fillTable(final List<String> countries) {
        countryListView.setCountries(countries);
    }

    @Override
    public void bind() {
        countryListView.setPresenter(this);
    }
}
