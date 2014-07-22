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
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.views.PersonView;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

public class PersonPresenterImpl extends AsyncDataProvider<PersonData> implements PersonPresenter {

    private final PersonView personView;
    private BackendServiceAsync backendService;

    @Inject
    public PersonPresenterImpl(PersonView personView) {
        this.personView = personView;
    }

    @Inject
    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }

    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        container.add(personView.asWidget());
    }

    @Override
    public void bind() {
        personView.setPresenter(this);
    }

    @Override
    protected void onRangeChanged(HasData<PersonData> display) {
        final Range range = display.getVisibleRange();

        backendService.getPersons(/*range.getStart(), range.getLength(), */new AsyncCallback<List<PersonData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<PersonData> personDataList) {
                for (PersonData personData : personDataList) {
                    GWT.log(personData.toString());
                }
                updateRowData(range.getStart(), personDataList);
            }
        });
    }

    @Override
    public void addPerson(PersonData personData) {
        GWT.log(personData.toString());
        backendService.addPerson(personData, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                personView.refreshGrid();
            }
        });
    }

    @Override
    public void deletePerson(PersonData personData) {
        backendService.deletePerson(personData, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                personView.refreshGrid();
            }
        });
    }
}
