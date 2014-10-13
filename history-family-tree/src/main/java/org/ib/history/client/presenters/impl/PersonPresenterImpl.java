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
import org.ib.history.client.views.person.PersonView;
import org.ib.history.commons.data.HouseData;
import org.ib.history.commons.data.PersonData;

import java.util.List;

public class PersonPresenterImpl extends AsyncDataProvider<PersonData> implements PersonPresenter {

    private final PersonView view;
    private BackendServiceAsync backendService;

    @Inject
    public PersonPresenterImpl(PersonView view) {
        this.view = view;
    }

    @Inject
    public void setBackendService(BackendServiceAsync backendService) {
        this.backendService = backendService;
    }

    @Override
    public void go(HasWidgets container) {
        bind();
        container.clear();
        view.refreshGrid();
        container.add(view.asWidget());
    }

    @Override
    public void bind() {
        view.setPresenter(this);
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
    public void addItem(PersonData personData) {
        GWT.log(personData.toString());
        backendService.addPerson(personData, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                view.refreshGrid();
            }
        });
    }

    @Override
    public void deleteItem(PersonData personData) {
        backendService.deletePerson(personData, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
                view.refreshGrid();
            }
        });
    }

    @Override
    public void setSelectedItem(PersonData item) {
        view.setSelectedItem(item);
    }

    @Override
    public void setPersonSuggestions(String pattern, final org.ib.history.client.utils.AsyncCallback<List<PersonData>> callback) {
        backendService.getPersonsByPattern(pattern, new AsyncCallback<List<PersonData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(List<PersonData> personDatas) {
                callback.onSuccess(personDatas);
            }
        });
    }

    @Override
    public void getPersonsByIds(List<PersonData> personsIdOnly, final org.ib.history.client.utils.AsyncCallback<List<PersonData>> callback) {
        backendService.getPersonsByIds(personsIdOnly, new AsyncCallback<List<PersonData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(List<PersonData> personDatas) {
                callback.onSuccess(personDatas);
            }
        });
    }

    @Override
    public void setParents(PersonData person, List<PersonData> parents, final org.ib.history.client.utils.AsyncCallback<Void> callback) {
        backendService.setParents(person, parents, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(null);
            }
        });
    }

    @Override
    public void setHouseSuggestions(String pattern, final org.ib.history.client.utils.AsyncCallback<List<HouseData>> callback) {
        backendService.getHousesByPattern(pattern, new AsyncCallback<List<HouseData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(List<HouseData> houseDatas) {
                callback.onSuccess(houseDatas);
            }
        });
    }

    @Override
    public void getHousesByIds(List<HouseData> housesIdOnly, final org.ib.history.client.utils.AsyncCallback<List<HouseData>> callback) {
        backendService.getHousesByIds(housesIdOnly, new AsyncCallback<List<HouseData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(List<HouseData> houseDatas) {
                callback.onSuccess(houseDatas);
            }
        });
    }

    @Override
    public void setHouses(PersonData person, List<HouseData> houses, final org.ib.history.client.utils.AsyncCallback<Void> callback) {
        backendService.setHouses(person, houses, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                callback.onFailure(throwable);
            }

            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(aVoid);
            }
        });
    }
}
