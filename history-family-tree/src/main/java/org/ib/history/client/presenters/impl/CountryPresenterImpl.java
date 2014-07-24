package org.ib.history.client.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.CountryPresenter;
import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

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
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(countryView.asWidget());
    }

    @Override
    public void bind() {
        countryView.setPresenter(this);
    }

    @Override
    protected void onRangeChanged(HasData<CountryData> display) {
        final Range range = display.getVisibleRange();

        backendService.getCountries("EN", /*range.getStart(), range.getLength(), */new AsyncCallback<List<CountryData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<CountryData> countryDataList) {
                for (CountryData countryData : countryDataList) {
                    GWT.log(countryData.toString());
                }
                updateRowData(range.getStart(), countryDataList);
            }
        });
    }

    @Override
    public void addCountry(CountryData countryData) {
        backendService.addCountry(countryData, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                countryView.refreshGrid();
            }
        });
    }

    @Override
    public void deleteCountry(CountryData countryData) {
        backendService.deleteCountry(countryData, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                countryView.refreshGrid();
            }
        });
    }
}
