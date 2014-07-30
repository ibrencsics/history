package org.ib.history.client.presenters.impl;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
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
        eventBus.fireEvent(new ScreenChangeEvent(ScreenChangeEvent.Screen.COUNTRY));
    }

    @Override
    public void onShowHousesClicked() {
        eventBus.fireEvent(new ScreenChangeEvent(ScreenChangeEvent.Screen.HOUSE));
    }

    @Override
    public void onShowPersonsClicked() {
        eventBus.fireEvent(new ScreenChangeEvent(ScreenChangeEvent.Screen.PERSON));
    }
}
