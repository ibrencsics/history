package org.ib.history.client.views.impl;

import org.ib.history.client.views.HouseView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.HouseData;

public class HouseViewImpl extends BaseCrudViewImpl<HouseData> implements HouseView {

    @Override
    protected ItemEditor<HouseData> getItemEditor() {
        return null;
    }

//    @Override
//    protected void buildEditColumns() {
//
//    }

    @Override
    protected void buildListColumns() {

    }

    @Override
    protected HouseData getEmptyItem() {
        return new HouseData.Builder().name("").build();
    }
}
