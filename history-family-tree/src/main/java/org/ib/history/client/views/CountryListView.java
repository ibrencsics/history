package org.ib.history.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import org.ib.history.client.presenters.CountryListPresenter;

import java.util.List;

public interface CountryListView extends IsWidget {
    void setPresenter(CountryListPresenter presenter);
    void setCountries(List<String> countries);
}
