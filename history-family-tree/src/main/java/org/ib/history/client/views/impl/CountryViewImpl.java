package org.ib.history.client.views.impl;

import com.google.gwt.user.client.ui.Label;
import org.ib.history.client.views.CountryView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.CountryData;

public class CountryViewImpl extends BaseCrudViewImpl<CountryData> implements CountryView {


    @Override
    protected ItemEditor<CountryData> getItemEditor() {
        return new ItemEditorImpl();
    }

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

    private class ItemEditorImpl extends ItemEditor<CountryData> {

        @Override
        protected void visualizeOthers() {
            flexTable.setWidget(0, 2, new Label("Stuff"));
        }
    }
}