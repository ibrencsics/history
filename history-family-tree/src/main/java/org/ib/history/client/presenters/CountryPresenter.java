package org.ib.history.client.presenters;

import org.ib.history.commons.data.CountryData;

public interface CountryPresenter extends Presenter {
    void addCountry(CountryData countryData);
    void deleteCountry(CountryData countryData);
}
