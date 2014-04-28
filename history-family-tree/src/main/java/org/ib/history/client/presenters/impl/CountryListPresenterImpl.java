package org.ib.history.client.presenters.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.views.CountryListView;
import org.ib.history.commons.data.CountryDto;

import java.util.List;

public class CountryListPresenterImpl extends AsyncDataProvider<CountryDto> implements CountryListPresenter {

    private final CountryListView countryListView;
    private BackendServiceAsync backendService;

    @Inject
    public CountryListPresenterImpl(CountryListView countryListView) {
        this.countryListView = countryListView;
//        bind();
    }

    @Inject
    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }


    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(countryListView.asWidget());

//        countryListView.refreshGrid();
    }

    @Override
    public void bind() {
        countryListView.setPresenter(this);
    }

    @Override
    public void deleteCountry(CountryDto countryDto) {
        backendService.deleteCountry(countryDto, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                GWT.log("Deleted");
                countryListView.refreshGrid();
            }
        });
    }


    @Override
    protected void onRangeChanged(HasData<CountryDto> countryDTOHasData) {
        backendService.getCountries(new AsyncCallback<List<CountryDto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<CountryDto> countryDtos) {
                updateRowData(0, countryDtos);
            }
        });
    }
}
