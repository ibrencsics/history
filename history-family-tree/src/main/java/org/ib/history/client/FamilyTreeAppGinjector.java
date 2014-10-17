package org.ib.history.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.ib.history.client.presenters.*;
import org.ib.history.client.views.country.CountryView;
import org.ib.history.client.views.house.HouseView;
import org.ib.history.client.views.person.PersonView;
import org.ib.history.client.views.pope.PopeView;
import org.ib.history.client.views.welcome.WelcomeView;

@GinModules(FamilyTreeGinModule.class)
public interface FamilyTreeAppGinjector extends Ginjector {
    EventBus getEventBus();
    BackendServiceAsync getBackendServices();

    WelcomeView getWelcomeView();
    WelcomePresenter getWelcomePresenter();

    CountryView getCountryView();
    CountryPresenter getCountryPresenter();
    HouseView getHouseView();
    HousePresenter getHousePresenter();
    PersonView getPersonView();
    PersonPresenter getPersonPresenter();
    PopeView getPopeView();
    PopePresenter getPopePresenter();
}
