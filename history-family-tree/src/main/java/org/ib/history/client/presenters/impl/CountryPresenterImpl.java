package org.ib.history.client.presenters.impl;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.CountryPresenter;
import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;

public class CountryPresenterImpl extends AsyncDataProvider<CountryData> implements CountryPresenter {

    private final CountryView countryView;
    private BackendServiceAsync backendService;

    @Inject
    public CountryPresenterImpl(CountryView countryView) {
        this.countryView = countryView;
    }

    @Inject
    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }

    @Override
    protected void onRangeChanged(HasData<CountryData> countryDataHasData) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();;
        container.add(countryView.asWidget());
    }

    @Override
    public void bind() {
        countryView.setPresenter(this);
    }
}
