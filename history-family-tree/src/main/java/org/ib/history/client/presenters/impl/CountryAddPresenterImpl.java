package org.ib.history.client.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.CountryAddPresenter;
import org.ib.history.client.views.CountryAddView;
import org.ib.history.commons.data.CountryData;

public class CountryAddPresenterImpl implements CountryAddPresenter {

    private CountryAddView countryAddView;

    private BackendServiceAsync backendService;

    @Inject
    public CountryAddPresenterImpl(CountryAddView countryAddView) {
        this.countryAddView = countryAddView;
        bind();
    }

    @Inject
    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(countryAddView.asWidget());
    }

    @Override
    public void bind() {
        countryAddView.setPresenter(this);
    }

    @Override
    public void addCountry(String name) {
        CountryData country = new CountryData();
        country.setName(name);

        backendService.addCountry(country, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                GWT.log(throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                GWT.log("Success");
            }
        });
    }
}
