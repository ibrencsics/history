package org.ib.history.client.views.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.Range;
import org.ib.history.client.presenters.CountryPresenter;
import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCellTableViewImpl<CountryData> implements CountryView {

    private CountryPresenter presenter;
    private CountryAddViewImpl countryAddView;

    @Override
    protected void buildColumns() {
        buildColumnName();
        buildColumnEdit();
        buildColumnDelete();

        cellTable.addColumn(columnName, buildHeader(COLUMN_NAME));
        cellTable.addColumn(columnEdit, buildHeader(COLUMN_EDIT));
        cellTable.addColumn(columnDelete, buildHeader(COLUMN_DELETE));
    }

    @Override
    protected void onEdit(CountryData data) {
        countryAddView.setCountryDataSelected(data);
    }

    @Override
    protected void onDelete(CountryData data) {
        presenter.deleteCountry(data);
    }

    @Override
    protected void buildAddItemPanel() {
        countryAddView = new CountryAddViewImpl();
        setAddItemForm(countryAddView);
    }

    @Override
    public void setPresenter(CountryPresenter presenter) {
        this.presenter = presenter;
        countryAddView.setPresenter(presenter);
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

    class CountryAddViewImpl extends Composite {

        private TextBox tbName;
        private Button btnSubmit;

        private CountryPresenter presenter;
        private CountryData countryDataSelected;

        CountryAddViewImpl() {
            FlowPanel panel = new FlowPanel();
            tbName = new TextBox();
            btnSubmit = new Button("Add");
            panel.add(tbName);
            panel.add(btnSubmit);

            initWidget(panel);

            bind();
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
                    presenter.addCountry(
                            new CountryData.Builder()
                                    .id(countryDataSelected != null ? countryDataSelected.getId() : null)
                                    .name(tbName.getText())
                                    .build());

                    countryDataSelected = null;
                    tbName.setText("");
                }
            });
        }

        void setCountryDataSelected(CountryData countryDataSelected) {
            this.countryDataSelected = countryDataSelected;
            tbName.setText(countryDataSelected.getName());
        }
    }
}