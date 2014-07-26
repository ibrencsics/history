package org.ib.history.client.views.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CountryPresenter;
import org.ib.history.client.presenters.CrudPresenter;
import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;

public class CountryViewImpl extends BaseCrudViewImpl<CountryData> implements CountryView {

    private CountryAddViewImpl countryAddView;

    @Override
    protected void buildEditColumns() {
//        localeProvider.getList().add(new CountryData.Builder().name("qqq").build());
        ctEdit.addColumn(buildColumnName(), buildHeader(COLUMN_NAME));
    }

    @Override
    protected void buildListColumns() {
        ctList.addColumn(buildColumnName(), buildHeader(COLUMN_NAME));
        ctList.addColumn(buildColumnEdit(), buildHeader(COLUMN_EDIT));
        ctList.addColumn(buildColumnDelete(), buildHeader(COLUMN_DELETE));
    }

//    @Override
//    protected void buildAddItemPanel() {
//        countryAddView = new CountryAddViewImpl();
//        setAddItemForm(countryAddView);
//    }


    class CountryAddViewImpl extends Composite {

        private FlexTable flexTable;

        private Button btnAddLocale;
        private Button btnSubmit;

        private CountryPresenter presenter;
        private CountryData countryDataSelected;

        CountryAddViewImpl() {
            VerticalPanel panel = new VerticalPanel();

            setupFlexTable(null);
            panel.add(flexTable);

            btnAddLocale = new Button("Add");
            panel.add(btnAddLocale);

            btnSubmit = new Button("Save");
            panel.add(btnSubmit);

            initWidget(panel);

            bind();
        }

        private void setupFlexTable(PersonData personData) {
            if (flexTable==null) {
                flexTable = new FlexTable();
            } else {
                flexTable.clear();
            }

            if (personData==null) {
                flexTable.setWidget(0, 0, new TextBox());
                flexTable.setWidget(0, 1, new Button("Delete"));
            } else {

                flexTable.setWidget(0, 0, new TextBox());
            }
        }

        public void setPresenter(CountryPresenter presenter) {
            this.presenter = presenter;
//            bind();
        }

        private void bind() {
            btnSubmit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    System.out.println("presenter " + presenter);
                    presenter.addItem(
                            new CountryData.Builder()
                                    .id(countryDataSelected != null ? countryDataSelected.getId() : null)
//                                    .name(tbName.getText())
                                    .build());

                    countryDataSelected = null;
//                    tbName.setText("");
                }
            });
        }

        void setCountryDataSelected(CountryData countryDataSelected) {
            this.countryDataSelected = countryDataSelected;
//            tbName.setText(countryDataSelected.getName());
        }
    }
}