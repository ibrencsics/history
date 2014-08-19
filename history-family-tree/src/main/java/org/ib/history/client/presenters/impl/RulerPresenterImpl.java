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
import org.ib.history.client.presenters.RulerPresenter;
import org.ib.history.client.views.RulerView;
import org.ib.history.commons.data.RulerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RulerPresenterImpl extends AsyncDataProvider<RulerData> implements RulerPresenter {

    private final RulerView view;
    private BackendServiceAsync backendService;

    @Inject
    public RulerPresenterImpl(RulerView view) {
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
        container.add(view.asWidget());
    }

    @Override
    public void bind() {
        view.setPresenter(this);
    }

    @Override
    protected void onRangeChanged(HasData<RulerData> display) {
        final Range range = display.getVisibleRange();

        backendService.getRulers(new AsyncCallback<Set<RulerData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Error " + throwable.getMessage());
            }

            @Override
            public void onSuccess(Set<RulerData> rulerDataList) {
                for (RulerData rulerData : rulerDataList) {
                    GWT.log(rulerData.toString());
                }
                updateRowData(range.getStart(), new ArrayList<RulerData>(rulerDataList));
            }
        });
    }

    @Override
    public void addItem(RulerData item) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteItem(RulerData item) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
