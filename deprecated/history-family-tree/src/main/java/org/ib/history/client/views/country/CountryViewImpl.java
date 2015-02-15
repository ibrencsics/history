package org.ib.history.client.views.country;

import org.ib.history.client.views.base.BaseList;
import org.ib.history.client.views.base.BaseCrudViewImpl;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.client.views.base.DefaultEditor;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCrudViewImpl<CountryData> implements CountryView {


    @Override
    protected BaseEditor<CountryData> getItemEditor() {
        return new DefaultEditor() {
            @Override
            protected CountryData getEmptyItem() {
                return new CountryData.Builder().name("").build();
            }
        };
    }

    @Override
    protected BaseList<CountryData> getItemLister() {
        return new BaseList<CountryData>() {
            @Override
            protected void buildListColumns() {

            }
        };
    }
}