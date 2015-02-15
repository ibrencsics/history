package org.ib.history.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import org.ib.history.client.event.ScreenChangeEvent;
import org.ib.history.client.event.ScreenChangeEventHandler;
//import org.ib.history.client.presenters.CountryAddPresenter;
//import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.presenters.WelcomePresenter;

import java.util.logging.Logger;

public class FamilyTreeApp implements ValueChangeHandler<String> {

    EventBus eventBus;
    HasWidgets container;

    private final FamilyTreeAppGinjector injector = GWT.create(FamilyTreeAppGinjector.class);


    public FamilyTreeApp() {
        this.eventBus = injector.getEventBus();
        bind();
    }

    private void doWelcome() {
        injector.getWelcomePresenter().go(container);
    }

    private void doShowCountries() {
        injector.getWelcomePresenter().show(injector.getCountryPresenter());
    }

    private void doShowHouses() {
        injector.getWelcomePresenter().show(injector.getHousePresenter());
    }

    private void doShowPersons() {
        injector.getWelcomePresenter().show(injector.getPersonPresenter());
    }

    private void doShowPopes() {
        injector.getWelcomePresenter().show(injector.getPopePresenter());
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(ScreenChangeEvent.TYPE, new ScreenChangeEventHandler() {
            @Override
            public void showCountry(ScreenChangeEvent event) {
                doShowCountries();
            }
            @Override
            public void showHouse(ScreenChangeEvent event) {
                doShowHouses();
            }
            @Override
            public void showPerson(ScreenChangeEvent event) {
                doShowPersons();
            }
            @Override
            public void showPope(ScreenChangeEvent event) {
                doShowPopes();
            }
        });
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();
        console("token: " + token);

        if ((token != null) && (!token.equals(Tokens.HOME))) {
            if (token.startsWith(Tokens.COUNTRY_LIST)) {
                doShowCountries();
            } else if (token.startsWith(Tokens.COUNTRY_ADD)) {
                doShowCountries();
            } else {
                doWelcome();
            }
        } else {
            doWelcome();
        }
    }

    public void go(HasWidgets container) {
        this.container = container;
        doWelcome();
    }

    public static native void console(String text)
    /*-{
        console.log(text);
    }-*/;
}
