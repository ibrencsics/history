package org.ib.history.client.views.impl;

import com.google.gwt.user.client.ui.Widget;
import org.ib.history.client.utils.SupportedLocale;
import org.ib.history.client.views.CountryView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.CountryData;

import java.util.ArrayList;
import java.util.List;

public class CountryViewImpl extends BaseCrudViewImpl<CountryData> implements CountryView {


    @Override
    protected ItemEditor<CountryData> getItemEditor() {
        return new DefaultItemEditorImpl() {
            @Override
            protected CountryData getEmptyItem() {
                return new CountryData.Builder().name("").build();
            }
        };
    }

    @Override
    protected void notifyCustomPanel(CountryData selected) {}

    @Override
    protected void buildListColumns() {

    }
}