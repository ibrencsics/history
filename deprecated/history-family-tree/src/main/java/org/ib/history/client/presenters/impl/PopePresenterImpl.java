package org.ib.history.client.presenters.impl;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.inject.Inject;
import org.ib.history.client.BackendServiceAsync;
import org.ib.history.client.presenters.PopePresenter;
import org.ib.history.client.views.pope.PopeView;
import org.ib.history.commons.data.PopeData;

import java.util.List;

public class PopePresenterImpl extends AsyncDataProvider<PopeData> implements PopePresenter {

    private final PopeView view;
    private BackendServiceAsync backendService;

    @Inject
    public PopePresenterImpl(PopeView view) {
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
    protected void onRangeChanged(HasData<PopeData> display) {
        final Range range = display.getVisibleRange();

        backendService.getPopes(range.getStart(), range.getLength(), new AsyncCallback<List<PopeData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(List<PopeData> popeDatas) {
                updateRowData(range.getStart(), popeDatas);
            }
        });
    }

    @Override
    public void addItem(PopeData item) {
        backendService.addPope(item, new AsyncCallback<Void>() {
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
    public void deleteItem(PopeData item) {
        backendService.deletePope(item, new AsyncCallback<Void>() {
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
    public void setSelectedItem(PopeData item) {
        view.setSelectedItem(item);
    }
}
