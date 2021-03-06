package org.ib.history.client.presenters;

import com.google.gwt.user.client.ui.Widget;

public interface WelcomePresenter extends Presenter {
    public void onShowCountriesClicked();
    public void onShowHousesClicked();
    public void onShowPersonsClicked();
    public void onShowPopesClicked();

    public void show(Presenter presenter);
}
