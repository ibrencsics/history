package org.ib.history.client.views.impl;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextButtonCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.presenters.impl.PersonPresenterImpl;
import org.ib.history.client.views.PersonView;
import org.ib.history.commons.data.FlexibleDate;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

public class PersonViewImpl extends BaseCellTableViewImpl<PersonData> implements PersonView {

    final String COLUMN_NAME_NAME = "Name";
    final String COLUMN_DATE_OF_BIRTH_NAME = "Date of birth";
    final String COLUMN_DATE_OF_DEATH_NAME = "Date of death";
    final String COLUMN_NAME_EDIT = "Edit";
    final String COLUMN_NAME_DELETE = "Delete";

    TextColumn<PersonData> columnName;
    TextColumn<PersonData> columnDateOfBirth;
    TextColumn<PersonData> columnDateOfDeath;
    Column<PersonData, String> columnEdit;
    Column<PersonData, String> columnDelete;

    private PersonPresenter presenter;
    private PersonAddViewImpl personAddView;

    @Override
    protected void buildColumns() {
        buildColumnName();
        buildColumnDateOfBirth();
        buildColumnDateOfDeath();
        buildColumnEdit();
        buildColumnDelete();

        cellTable.addColumn(columnName, buildHeader(COLUMN_NAME_NAME), buildHeader(COLUMN_NAME_NAME));
        cellTable.addColumn(columnDateOfBirth, buildHeader(COLUMN_DATE_OF_BIRTH_NAME), buildHeader(COLUMN_DATE_OF_BIRTH_NAME));
        cellTable.addColumn(columnDateOfDeath, buildHeader(COLUMN_DATE_OF_DEATH_NAME), buildHeader(COLUMN_DATE_OF_DEATH_NAME));
        cellTable.addColumn(columnEdit, buildHeader(COLUMN_NAME_EDIT), detailFooter);
        cellTable.addColumn(columnDelete, buildHeader(COLUMN_NAME_DELETE), detailFooter);
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
                return GwtDateFormat.convert(personData.getDateOfBirth());
            }
        };
        columnDateOfBirth.setDataStoreName(COLUMN_DATE_OF_BIRTH_NAME);
    }

    private void buildColumnDateOfDeath() {
        columnDateOfDeath = new TextColumn<PersonData>() {
            @Override
            public String getValue(PersonData personData) {
                return GwtDateFormat.convert(personData.getDateOfDeath());
            }
        };
        columnDateOfDeath.setDataStoreName(COLUMN_DATE_OF_DEATH_NAME);
    }

    private Column<PersonData, String> buildColumnEdit() {
        columnEdit = new Column<PersonData, String>(new TextButtonCell()) {
            @Override
            public String getValue(PersonData object) {
                return "Edit";
            }
        };
        columnEdit.setDataStoreName(COLUMN_NAME_EDIT);
        columnEdit.setFieldUpdater(new FieldUpdater<PersonData, String>() {
            @Override
            public void update(int index, PersonData object, String value) {
                GWT.log(object.getName() + " pressed");
                personAddView.setPersonDataSelected(object);
            }
        });
        return columnEdit;
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

    class PersonAddViewImpl extends Composite {

        private TextBox tbName;
        private TextBox tbBirth;
        private TextBox tbDeath;
        private Button btmSubmit;

        private PersonPresenter presenter;
        private PersonData personDataSelected;

        public PersonAddViewImpl() {
            FlowPanel panel = new FlowPanel();
            tbName = new TextBox();
            tbBirth = new TextBox();
            tbDeath = new TextBox();
            btmSubmit = new Button("Add");
            panel.add(tbName);
            panel.add(tbBirth);
            panel.add(tbDeath);
            panel.add(btmSubmit);

            initWidget(panel);
        }

        public void setPresenter(PersonPresenter presenter) {
            this.presenter = presenter;
            bind();
        }

        private void bind() {
            btmSubmit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    System.out.println("presenter " + presenter);
                    presenter.addPerson(
                            new PersonData.Builder()
                                    .id(personDataSelected!=null ? personDataSelected.getId() : null)
                                    .name(tbName.getText())
                                    .dateOfBirth(GwtDateFormat.convert(tbBirth.getText()))
                                    .dateOfDeath(GwtDateFormat.convert(tbDeath.getText()))
                                    .build());

                    personDataSelected = null;
                    tbName.setText("");
                    tbBirth.setText("");
                    tbDeath.setText("");
                }
            });
        }

        void setPersonDataSelected(PersonData personDataSelected) {
            this.personDataSelected = personDataSelected;
            tbName.setText(personDataSelected.getName());
            tbBirth.setText(GwtDateFormat.convert(personDataSelected.getDateOfBirth()));
            tbDeath.setText(GwtDateFormat.convert(personDataSelected.getDateOfDeath()));
        }
    }
}