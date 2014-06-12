package org.ib.history.client.presenters.impl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import org.ib.history.client.Tokens;
import org.ib.history.client.event.ScreenChangeEvent;
import org.ib.history.client.presenters.Presenter;
import org.ib.history.client.presenters.WelcomePresenter;
import org.ib.history.client.views.WelcomeView;

public class WelcomePresenterImpl implements WelcomePresenter {

    private final WelcomeView welcomeView;
    private final EventBus eventBus;

    @Inject
    public WelcomePresenterImpl(WelcomeView welcomeView, EventBus eventBus) {
        this.welcomeView = welcomeView;
        this.eventBus = eventBus;
        bind();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(welcomeView.asWidget());
    }

    @Override
    public void show(Presenter presenter) {
        presenter.go(welcomeView.getWorkspace());
    }

    @Override
    public void bind() {
        welcomeView.setPresenter(this);
    }

    @Override
    public void onShowCountriesClicked() {
//        History.newItem(Tokens.COUNTRY_LIST);
        eventBus.fireEvent(new ScreenChangeEvent(ScreenChangeEvent.Screen.COUNTRY_LIST));
    }

    @Override
    public void onNewCountryClicked() {
//        History.newItem(Tokens.COUNTRY_ADD);
        eventBus.fireEvent(new ScreenChangeEvent(ScreenChangeEvent.Screen.COUNTRY_ADD));
    }

    @Override
    public void onShowPersonsClicked() {
        eventBus.fireEvent(new ScreenChangeEvent(ScreenChangeEvent.Screen.PERSON_LIST));
    }

    @Override
    public void onNewPersonClicked() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
