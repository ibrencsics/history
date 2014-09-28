package org.ib.history.client.views.house;

import org.ib.history.client.views.base.BaseCrudViewImpl;
import org.ib.history.client.views.base.BaseList;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.client.views.base.DefaultEditor;
import org.ib.history.commons.data.HouseData;

public class HouseViewImpl extends BaseCrudViewImpl<HouseData> implements HouseView {

    @Override
    protected BaseEditor<HouseData> getItemEditor() {
        return new DefaultEditor() {
            @Override
            protected HouseData getEmptyItem() {
                return new HouseData.Builder().name("").build();
            }
        };
    }

    @Override
    protected BaseList<HouseData> getItemLister() {
        return new BaseList<HouseData>() {
            @Override
            protected void buildListColumns() {

            }
        };
    }
}
