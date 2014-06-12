package org.ib.history.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.ib.history.client.presenters.CountryAddPresenter;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.presenters.PersonPresenter;
import org.ib.history.client.presenters.WelcomePresenter;
import org.ib.history.client.views.CountryAddView;
import org.ib.history.client.views.CountryListView;
import org.ib.history.client.views.PersonView;
import org.ib.history.client.views.WelcomeView;

@GinModules(FamilyTreeGinModule.class)
public interface FamilyTreeAppGinjector extends Ginjector {
    EventBus getEventBus();
    BackendServiceAsync getBackendServices();

    WelcomeView getWelcomeView();
    WelcomePresenter getWelcomePresenter();
    CountryListView getCountryListView();
    CountryListPresenter getCountryListPresenter();
    CountryAddView getCountryAddView();
    CountryAddPresenter getCountryAddPresenter();
    PersonView getPersonView();
    PersonPresenter getPersonPresenter();
}
