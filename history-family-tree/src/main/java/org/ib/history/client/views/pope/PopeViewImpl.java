package org.ib.history.client.views.pope;

import org.ib.history.client.views.base.BaseCrudViewImpl;
import org.ib.history.client.views.base.BaseEditor;
import org.ib.history.client.views.base.BaseList;
import org.ib.history.client.views.base.DefaultEditor;
import org.ib.history.commons.data.PopeData;

public class PopeViewImpl extends BaseCrudViewImpl<PopeData> implements PopeView {

    @Override
    protected BaseList<PopeData> getItemLister() {
        return new PopeList();
    }

    @Override
    protected BaseEditor<PopeData> getItemEditor() {
        return new DefaultEditor<PopeData>() {
            @Override
            protected PopeData getEmptyItem() {
                return new PopeData.Builder().name("").build();
            }
        };
    }
}
