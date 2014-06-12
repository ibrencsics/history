package org.ib.history.client.presenters;

import com.google.gwt.user.client.ui.Widget;

public interface WelcomePresenter extends Presenter {
    public void onShowCountriesClicked();
    public void onNewCountryClicked();
    public void onShowPersonsClicked();
    public void onNewPersonClicked();

    public void show(Presenter presenter);
}
