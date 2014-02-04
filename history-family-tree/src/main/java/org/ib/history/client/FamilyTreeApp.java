package org.ib.history.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.presenters.WelcomePresenter;

public class FamilyTreeApp implements ValueChangeHandler<String> {

    EventBus eventBus;
    HasWidgets container;

    private final FamilyTreeAppGinjector injector = GWT.create(FamilyTreeAppGinjector.class);


    public FamilyTreeApp() {
        this.eventBus = injector.getEventBus();
         bind();
    }

    private void doWelcome() {
        WelcomePresenter pres = injector.getWelcomePresenter();
        pres.go(container);
    }

    private void doShowCountries() {
        CountryListPresenter pres = injector.getCountryListPresenter();
        pres.go(container);
    }

    private void doShowNewCountry() {

    }

    private void bind() {
        History.addValueChangeHandler(this);
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        String token = event.getValue();

        if ((token != null) && (!token.equals(Tokens.HOME))) {
            if (token.startsWith(Tokens.COUNTRY_LIST)) {
                doShowCountries();
            } else if (token.startsWith(Tokens.COUNTRY_ADD)) {
                doShowNewCountry();
            }
        } else {
            doWelcome();
        }
    }

    public void go(HasWidgets container) {
        this.container = container;
    }
}
