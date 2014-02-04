package org.ib.history.client.presenters.impl;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import org.ib.history.client.Tokens;
import org.ib.history.client.presenters.WelcomePresenter;
import org.ib.history.client.views.WelcomeView;

public class WelcomePresenterImpl implements WelcomePresenter {

    private final WelcomeView welcomeView;

    @Inject
    public WelcomePresenterImpl(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
        bind();
    }

    @Override
    public void go(HasWidgets container) {
        container.clear();
        container.add(welcomeView.asWidget());
    }

    @Override
    public void bind() {
        welcomeView.setPresenter(this);
    }

    @Override
    public void onShowCountriesClicked() {
        History.newItem(Tokens.COUNTRY_LIST);
    }

    @Override
    public void onNewCountryClicked() {
        History.newItem(Tokens.COUNTRY_ADD);
    }
}
