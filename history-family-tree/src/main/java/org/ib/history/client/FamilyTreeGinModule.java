package org.ib.history.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.ib.history.client.presenters.*;
import org.ib.history.client.presenters.impl.*;
import org.ib.history.client.views.*;
import org.ib.history.client.views.impl.*;
import org.ib.history.commons.data.CountryData;

public class FamilyTreeGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(WelcomeView.class).to(WelcomeViewImpl.class).in(Singleton.class);

        bind(CountryView.class).to(CountryViewImpl.class).in(Singleton.class);
        bind(HouseView.class).to(HouseViewImpl.class).in(Singleton.class);
        bind(PersonView.class).to(PersonViewImpl.class).in(Singleton.class);

        bind(WelcomePresenter.class).to(WelcomePresenterImpl.class);

        bind(CountryPresenter.class).to(CountryPresenterImpl.class);
        bind(HousePresenter.class).to(HousePresenterImpl.class);
        bind(PersonPresenter.class).to(PersonPresenterImpl.class);

        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    }
}
