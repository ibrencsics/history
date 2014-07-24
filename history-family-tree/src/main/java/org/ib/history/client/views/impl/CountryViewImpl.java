package org.ib.history.client.views.impl;

import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CountryPresenter;
import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCellTableViewImpl<CountryData> implements CountryView {

    private CountryPresenter presenter;

    @Override
    protected void buildColumns() {
        buildColumnName();
        buildColumnDelete();

        cellTable.addColumn(columnName, buildHeader(COLUMN_NAME));
        cellTable.addColumn(columnDelete, buildHeader(COLUMN_DELETE));
    }

    @Override
    protected void onEdit(CountryData data) {

    }

    @Override
    protected void onDelete(CountryData data) {

    }

    @Override
    protected void buildAddItemPanel() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPresenter(CountryPresenter presenter) {
        this.presenter = presenter;
        createWithAsyncDataProvider();
    }

    private void createWithAsyncDataProvider() {
        ((AsyncDataProvider<CountryData>)presenter).addDataDisplay(cellTable);
    }

    @Override
    public void refreshGrid() {
        cellTable.setVisibleRangeAndClearData(new Range(0, 25), true);
        cellTable.redraw();
    }
}