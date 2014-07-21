package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.presenters.impl.PersonPresenterImpl;
import org.ib.history.client.views.PersonView;
import org.ib.history.client.views.create.PersonAddViewImpl;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.Neo4jDateFormat;

public class PersonViewImpl extends BaseCellTableViewImpl<PersonData> implements PersonView {

    final String COLUMN_NAME_NAME = "Name";
    final String COLUMN_DATE_OF_BIRTH_NAME = "Date of birth";
    final String COLUMN_DATE_OF_DEATH_NAME = "Date of death";
    final String COLUMN_NAME_DELETE = "Delete";

    TextColumn<PersonData> columnName;
    TextColumn<PersonData> columnDateOfBirth;
    Column<PersonData, String> columnDelete;

    private PersonPresenter presenter;
    private PersonAddViewImpl personAddView;

    @Override
    protected void buildColumns() {
        buildColumnName();
        buildColumnDateOfBirth();
        buildColumnDelete();

        cellTable.addColumn(columnName,  buildHeader(COLUMN_NAME_NAME), buildHeader(COLUMN_NAME_NAME));
        cellTable.addColumn(columnDateOfBirth, buildHeader(COLUMN_DATE_OF_BIRTH_NAME), buildHeader(COLUMN_DATE_OF_BIRTH_NAME));
        cellTable.addColumn(columnDelete,  buildHeader(COLUMN_NAME_DELETE), detailFooter);
    }

    private void buildColumnName() {
        columnName = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData object) {
                return object.getName();
            }
        };
        columnName.setDataStoreName(COLUMN_NAME_NAME);
    }

    private void buildColumnDateOfBirth() {
        columnDateOfBirth = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return personData.getDateOfBirth().getValue();
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH_NAME);
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
                presenter.deletePerson(object);
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
    protected void buildAddItemPanel() {
        personAddView = new PersonAddViewImpl();
        setAddItemForm(personAddView);
    }

    @Override
    public void setPresenter(PersonPresenterImpl presenter) {
        this.presenter = presenter;
        personAddView.setPresenter(presenter);
        createWithAsyncDataProvider();
    }

    private void createWithAsyncDataProvider() {
        ((AsyncDataProvider<PersonData>)presenter).addDataDisplay(cellTable);
    }

    @Override
    public void refreshGrid() {
        cellTable.setVisibleRangeAndClearData(new Range(0, 25), true);
        cellTable.redraw();
    }
}
