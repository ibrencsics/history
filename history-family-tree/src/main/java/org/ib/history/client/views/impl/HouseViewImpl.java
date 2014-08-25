package org.ib.history.client.views.impl;

import org.ib.history.client.views.HouseView;
import org.ib.history.client.widget.Editor;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.CountryData;
import org.ib.history.commons.data.HouseData;

public class HouseViewImpl extends BaseCrudViewImpl<HouseData> implements HouseView {

    @Override
    protected ItemEditor<HouseData> getItemEditor() {
        return new DefaultItemEditorImpl() {
            @Override
            protected HouseData getEmptyItem() {
                return new HouseData.Builder().name("").build();
            }
        };
    }

    @Override
    protected void buildListColumns() {

    }
}
