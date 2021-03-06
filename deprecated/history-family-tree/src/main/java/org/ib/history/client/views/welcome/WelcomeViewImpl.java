package org.ib.history.client.views.welcome;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import org.ib.history.client.presenters.WelcomePresenter;

public class WelcomeViewImpl extends Composite implements WelcomeView {

    interface WelcomeViewUiBinder extends UiBinder<Widget, WelcomeViewImpl> {}
    private static WelcomeViewUiBinder uiBinder = GWT.create(WelcomeViewUiBinder.class);

    private WelcomePresenter presenter;

    @UiField
    Button country;
    @UiField
    Button house;
    @UiField
    Button person;
    @UiField
    Button pope;
    @UiField
    FlowPanel workspace;

    public WelcomeViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("country")
    public void showCountries(ClickEvent event) {
        if (presenter != null) {
            presenter.onShowCountriesClicked();
        }
    }

    @UiHandler("house")
    public void showHouses(ClickEvent event) {
        if (presenter != null) {
            presenter.onShowHousesClicked();
        }
    }

    @UiHandler("person")
    public void showPersons(ClickEvent event) {
        if (presenter != null) {
            presenter.onShowPersonsClicked();
        }
    }

    @UiHandler("pope")
    public void showPope(ClickEvent event) {
        if (presenter != null) {
            presenter.onShowPopesClicked();
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
