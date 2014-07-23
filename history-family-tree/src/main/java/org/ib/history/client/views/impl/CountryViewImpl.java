package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CountryPresenter;
import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCellTableViewImpl<CountryData> implements CountryView {

    final String COLUMN_NAME = "Name";
    final String COLUMN_DELETE = "Delete";

    TextColumn<CountryData> columnName;
    Column<CountryData, String> columnDelete;

    private CountryPresenter presenter;

    @Override
    protected void buildColumns() {
        buildColumnName();
        buildColumnDelete();

        cellTable.addColumn(columnName, buildHeader(COLUMN_NAME), buildHeader(COLUMN_NAME));
        cellTable.addColumn(columnDelete, buildHeader(COLUMN_DELETE), detailFooter);
    }

    private void buildColumnName() {
        columnName = new TextColumn<CountryData>() {
            @Override
            public String getValue(CountryData object) {
                return object.getName();
            }
        };
        columnName.setDataStoreName(COLUMN_NAME);
    }

    private void buildColumnDelete() {
        columnDelete = new Column<CountryData, String>(new TextButtonCell()) {
            @Override
            public String getValue(CountryData object) {
                return "Delete";
            }
        };
        columnDelete.setDataStoreName(COLUMN_DELETE);
        columnDelete.setFieldUpdater(new FieldUpdater<CountryData, String>() {
            @Override
            public void update(int index, CountryData object, String value) {
                GWT.log(object.getName() + " pressed");
            }
        });
    }

    private Header<String> buildHeader(final String text) {
        Header<String> head = new Header<String>(new TextCell()) {
            @Override
            public String getValue() {
                return text;
            }
        };
        return head;
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
