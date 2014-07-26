package org.ib.history.client.views.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.presenters.impl.PersonPresenterImpl;
import org.ib.history.client.views.PersonView;
import org.ib.history.commons.data.PersonData;
import org.ib.history.commons.utils.GwtDateFormat;

public class PersonViewImpl extends BaseCrudViewImpl<PersonData> implements PersonView {

    final String COLUMN_DATE_OF_BIRTH_NAME = "Date of birth";
    final String COLUMN_DATE_OF_DEATH_NAME = "Date of death";


    TextColumn<PersonData> columnDateOfBirth;
    TextColumn<PersonData> columnDateOfDeath;

    private PersonAddViewImpl personAddView;

    @Override
    protected void buildEditColumns() {

    }

    @Override
    protected void buildListColumns() {
        buildColumnDateOfBirth();
        buildColumnDateOfDeath();

        ctList.addColumn(buildColumnName(), buildHeader(COLUMN_NAME));
        ctList.addColumn(columnDateOfBirth, buildHeader(COLUMN_DATE_OF_BIRTH_NAME));
        ctList.addColumn(columnDateOfDeath, buildHeader(COLUMN_DATE_OF_DEATH_NAME));
        ctList.addColumn(buildColumnEdit(), buildHeader(COLUMN_EDIT));
        ctList.addColumn(buildColumnEdit(), buildHeader(COLUMN_DELETE));
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

    @Override
    protected void onEdit(PersonData personData) {
        personAddView.setPersonDataSelected(personData);
    }

    @Override
    protected void onDelete(PersonData personData) {
        presenter.deleteItem(personData);
    }

//    @Override
//    protected void buildAddItemPanel() {
//        personAddView = new PersonAddViewImpl();
//        setAddItemForm(personAddView);
//    }

    private void createWithAsyncDataProvider() {
        ((AsyncDataProvider<PersonData>)presenter).addDataDisplay(ctList);
    }


    class PersonAddViewImpl extends Composite {

        private TextBox tbName;
        private TextBox tbBirth;
        private TextBox tbDeath;
        private Button btnSubmit;

        private PersonPresenter presenter;
        private PersonData personDataSelected;

        public PersonAddViewImpl() {
            FlowPanel panel = new FlowPanel();
            tbName = new TextBox();
            tbBirth = new TextBox();
            tbDeath = new TextBox();
            btnSubmit = new Button("Add");
            panel.add(tbName);
            panel.add(tbBirth);
            panel.add(tbDeath);
            panel.add(btnSubmit);

            initWidget(panel);

            bind();
        }

        public void setPresenter(PersonPresenter presenter) {
            this.presenter = presenter;
//            bind();
        }

        private void bind() {
            btnSubmit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    System.out.println("presenter " + presenter);
                    presenter.addItem(
                            new PersonData.Builder()
                                    .id(personDataSelected != null ? personDataSelected.getId() : null)
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