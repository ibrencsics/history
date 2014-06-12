package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.AsyncDataProvider;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.presenters.impl.PersonPresenterImpl;
import org.ib.history.client.views.PersonView;
import org.ib.history.commons.data.PersonData;

public class PersonViewImpl extends BaseCellTableViewImpl<PersonData> implements PersonView {

    final String COLUMN_NAME_NAME = "Name";
    final String COLUMN_NAME_DELETE = "Delete";

    TextColumn<PersonData> columnName;
    Column<PersonData, String> columnDelete;

    private PersonPresenter presenter;

    @Override
    protected void buildColumns() {
        columnName = buildColumnName();
        columnDelete = buildColumnDelete();

        cellTable.addColumn(columnName,  buildHeader(COLUMN_NAME_NAME), buildHeader(COLUMN_NAME_NAME));
        cellTable.addColumn(columnDelete,  buildHeader(COLUMN_NAME_DELETE), detailFooter);
    }

    private TextColumn<PersonData> buildColumnName() {
        columnName = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData object) {
                return object.getName();
            }
        };
        columnName.setDataStoreName(COLUMN_NAME_NAME);
        return columnName;
    }

    private Column<PersonData, String> buildColumnDelete() {
        columnDelete = new Column<PersonData, String>(new TextButtonCell()) {
            @Override
            public String getValue(PersonData object) {
                return "Delete";
            }
        };
        columnDelete.setDataStoreName(COLUMN_NAME_DELETE);
        columnDelete.setFieldUpdater(new FieldUpdater<PersonData, String>() {
            @Override
            public void update(int index, PersonData object, String value) {
                // presenter.onSelectPhotoClicked(object.getId());
                GWT.log(object.getName() + " pressed");
//                presenter.deleteCountry(object);
            }
        });
        return columnDelete;
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
    public void setPresenter(PersonPresenterImpl presenter) {
        this.presenter = presenter;
        createWithAsyncDataProvider();
    }

    private void createWithAsyncDataProvider() {
        ((AsyncDataProvider<PersonData>)presenter).addDataDisplay(cellTable);
    }
}
