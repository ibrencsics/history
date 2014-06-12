package org.ib.history.client.presenters.impl;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.views.PersonView;
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
    protected void onRangeChanged(HasData<PersonData> personDataHasData) {
        backendService.getPersons(new AsyncCallback<List<PersonData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<PersonData> personDataList) {
                updateRowData(0, personDataList);
            }
        });
    }
}
