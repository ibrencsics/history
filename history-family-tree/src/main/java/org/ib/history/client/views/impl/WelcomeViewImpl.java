package org.ib.history.client.views.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.WelcomePresenter;
import org.ib.history.client.views.WelcomeView;

public class WelcomeViewImpl extends Composite implements WelcomeView {

    interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeViewImpl> {}
    private static WelcomeViewUiBinder uiBinder = GWT.create(WelcomeViewUiBinder.class);

    private WelcomePresenter presenter;

    @UiField
    Button showCountries;
    @UiField
    Button newCountry;
    @UiField
    FlowPanel workspace;

    public WelcomeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("showCountries")
    public void showCountries(ClickEvent event) {
        if (presenter != null) {
            presenter.onShowCountriesClicked();
        }
    }

    @UiHandler("newCountry")
    public void newCountry(ClickEvent event) {
        if (presenter != null) {
            presenter.onNewCountryClicked();
        }
    }

    @Override
    public void setPresenter(WelcomePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public HasWidgets getWorkspace() {
        return workspace;
    }
}