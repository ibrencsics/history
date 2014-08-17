package org.ib.history.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.ib.history.client.presenters.*;
import org.ib.history.client.views.*;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.PersonData;

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
    RulerView getRulerView();
    RulerPresenter getRulerPresenter();
}
