package org.ib.history.client.presenters;

import org.ib.history.commons.data.CountryDto;

public interface CountryListPresenter extends Presenter {
    void deleteCountry(CountryDto countryDto);
}
