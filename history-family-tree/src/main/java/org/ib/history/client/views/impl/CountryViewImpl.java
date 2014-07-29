package org.ib.history.client.views.impl;

import org.ib.history.client.views.CountryView;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCrudViewImpl<CountryData> implements CountryView {

    @Override
    protected void buildEditColumns() {

    }

    @Override
    protected void buildListColumns() {

    }

    @Override
    protected CountryData getEmptyItem() {
        return new CountryData.Builder().name("").build();
    }
}