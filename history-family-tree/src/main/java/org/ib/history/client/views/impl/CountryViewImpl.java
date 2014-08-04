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
        return new ItemEditorImpl();
    }

//    @Override
//    protected void buildEditColumns() {
//
//    }

    @Override
    protected void buildListColumns() {

    }


    private class ItemEditorImpl extends ItemEditor<CountryData> {

        @Override
        protected CountryData getEmptyItem() {
            return new CountryData.Builder().name("").build();
        }

        @Override
        protected List<String> getHeaders() {
            return new ArrayList<String>(0);
        }

        @Override
        protected List<Widget> getDefaultLocaleWidgets() {
            return new ArrayList<Widget>(0);
        }

        @Override
        protected List<Widget> getLocaleWidgets(SupportedLocale locale) {
            return new ArrayList<Widget>(0);
        }

        @Override
        protected void updateDefaultLocale(List<Widget> widgets) {

        }

        @Override
        protected void updateLocale(SupportedLocale locale, List<Widget> widgets) {

        }
    }
}