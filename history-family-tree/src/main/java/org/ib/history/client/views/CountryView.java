package org.ib.history.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.CountryPresenter;

public interface CountryView extends IsWidget {
    void setPresenter(CountryPresenter presenter);
    void refreshGrid();
}
