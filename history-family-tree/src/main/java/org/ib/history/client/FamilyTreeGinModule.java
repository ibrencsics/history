package org.ib.history.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.ib.history.client.presenters.*;
import org.ib.history.client.presenters.impl.*;
import org.ib.history.client.views.*;
import org.ib.history.client.views.impl.*;

public class FamilyTreeGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(WelcomeView.class).to(WelcomeViewImpl.class).in(Singleton.class);
//        bind(CountryListView.class).to(CountryListViewImpl.class).in(Singleton.class);
//        bind(CountryAddView.class).to(CountryAddViewImpl.class).in(Singleton.class);
        bind(CountryView.class).to(CountryViewImpl.class).in(Singleton.class);
        bind(PersonView.class).to(PersonViewImpl.class).in(Singleton.class);

        bind(WelcomePresenter.class).to(WelcomePresenterImpl.class);
//        bind(CountryListPresenter.class).to(CountryListPresenterImpl.class);
//        bind(CountryAddPresenter.class).to(CountryAddPresenterImpl.class);
        bind(CountryPresenter.class).to(CountryPresenterImpl.class);
        bind(PersonPresenter.class).to(PersonPresenterImpl.class);

        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    }
}
