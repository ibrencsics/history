package org.ib.history.client.views.impl;

import org.ib.history.client.views.RulerView;
import org.ib.history.client.widget.ItemEditor;
import org.ib.history.commons.data.RulerData;

public class RulerViewImpl extends BaseCrudViewImpl<RulerData> implements RulerView {

    @Override
    protected ItemEditor<RulerData> getItemEditor() {
        return null;
    }

    @Override
    protected void buildListColumns() {

    }
}
