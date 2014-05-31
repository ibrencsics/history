package org.ib.history.client.presenters;

import org.ib.history.commons.data.CountryData;

public interface CountryListPresenter extends Presenter {
    void deleteCountry(CountryData countryData);
}
