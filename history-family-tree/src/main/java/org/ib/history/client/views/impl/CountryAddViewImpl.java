package org.ib.history.client.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.presenters.CountryAddPresenter;
import org.ib.history.client.views.CountryAddView;

public class CountryAddViewImpl extends Composite implements CountryAddView {

    interface CountryAddUiBinder extends UiBinder<Widget, CountryAddViewImpl> {}
    private static CountryAddUiBinder uiBinder = GWT.create(CountryAddUiBinder.class);

    private CountryAddPresenter presenter;

    @UiField
    TextBox tbCountry;

    public CountryAddViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(CountryAddPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("btnAdd")
    public void addCountry(ClickEvent event) {
        String countryName = tbCountry.getText();
        presenter.addCountry(countryName);
    }
}
