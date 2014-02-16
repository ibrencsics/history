package org.ib.history.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import org.ib.history.client.presenters.CountryAddPresenter;
import org.ib.history.client.presenters.CountryListPresenter;
import org.ib.history.client.presenters.WelcomePresenter;
import org.ib.history.client.presenters.impl.CountryAddPresenterImpl;
import org.ib.history.client.presenters.impl.CountryListPresenterImpl;
import org.ib.history.client.presenters.impl.WelcomePresenterImpl;
import org.ib.history.client.views.CountryAddView;
import org.ib.history.client.views.CountryListView;
import org.ib.history.client.views.WelcomeView;
import org.ib.history.client.views.impl.CountryAddViewImpl;
import org.ib.history.client.views.impl.CountryListViewImpl;
import org.ib.history.client.views.impl.WelcomeViewImpl;

public class FamilyTreeGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(WelcomeView.class).to(WelcomeViewImpl.class).in(Singleton.class);
        bind(CountryListView.class).to(CountryListViewImpl.class).in(Singleton.class);
        bind(CountryAddView.class).to(CountryAddViewImpl.class).in(Singleton.class);

        bind(WelcomePresenter.class).to(WelcomePresenterImpl.class);
        bind(CountryListPresenter.class).to(CountryListPresenterImpl.class);
        bind(CountryAddPresenter.class).to(CountryAddPresenterImpl.class);

        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
    }
}
