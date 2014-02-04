package org.ib.history.client.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.views.CountryListView;

import java.util.List;

public class CountryListViewImpl extends Composite implements CountryListView {

    interface CountryListUiBinder extends UiBinder<Widget, CountryListViewImpl> {}
    private static CountryListUiBinder uiBinder = GWT.create(CountryListUiBinder.class);

    private CountryListPresenter presenter;

    @UiField
    FlexTable tableCountries;

    public CountryListViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
        tableCountries.setWidget(0, 0, new Label("szeva"));
    }

    @Override
    public void setPresenter(CountryListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setCountries(List<String> countries) {
        tableCountries.clear();
        int row=0;

        for (String country : countries) {
            tableCountries.setWidget(row++, 0, new Label(country));
        }
    }
}
