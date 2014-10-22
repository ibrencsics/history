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
import org.ib.history.client.presenters.HousePresenter;
import org.ib.history.client.views.house.HouseView;
import org.ib.history.commons.data.HouseData;

import java.util.List;

public class HousePresenterImpl extends AsyncDataProvider<HouseData> implements HousePresenter {

    private final HouseView view;
    private BackendServiceAsync backendService;

    @Inject
    public HousePresenterImpl(HouseView view) {
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
    protected void onRangeChanged(HasData<HouseData> display) {
        final Range range = display.getVisibleRange();

        backendService.getHouses(range.getStart(), range.getLength(), new AsyncCallback<List<HouseData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<HouseData> houseDataList) {
                updateRowData(range.getStart(), houseDataList);
            }
        });
    }

    @Override
    public void addItem(HouseData houseData) {
        backendService.addHouse(houseData, new AsyncCallback<Void>() {
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
    public void deleteItem(HouseData houseData) {
        backendService.deleteHouse(houseData, new AsyncCallback<Void>() {
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
    public void setSelectedItem(HouseData item) {
        view.setSelectedItem(item);
    }
}
